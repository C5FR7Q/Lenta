package com.example.slava.lenta2.views.fragment_main.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.slava.lenta2.Constants;
import com.example.slava.lenta2.adapters.RvAdapterMain;
import com.example.slava.lenta2.client.Data;
import com.example.slava.lenta2.client.LentaClient;
import com.example.slava.lenta2.views.activity.presenter.IMainPresenter;
import com.example.slava.lenta2.views.fragment_details.view.DetailsFragment;
import com.example.slava.lenta2.views.fragment_main.view.IFragmentView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by slava on 06.09.2017.
 */

public class FragmentPresenter implements IFragmentPresenter{
    private RvAdapterMain adapter;
    private IFragmentView fragmentView;
    private IMainPresenter mainPresenter;

    public FragmentPresenter(IFragmentView fragmentView, IMainPresenter mainPresenter) {
        this.fragmentView = fragmentView;
        this.mainPresenter = mainPresenter;
        ArrayList<String> titles = initTitles();
        Observable.zip(
                            LentaClient.getInstance().get(0),
                            LentaClient.getInstance().get(1),
                            LentaClient.getInstance().get(2),
                            (datas1, datas2, datas3) -> {
                                List<List<Data>> datas = new ArrayList<>();
                                datas.add(datas1);
                                datas.add(datas2);
                                datas.add(datas3);
                                return datas;
                            }
                        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lists -> {
                    adapter = new RvAdapterMain(titles, this, this, lists);
                    fragmentView.setAdapter(adapter);
                });
        Log.d("FragmentPresenter", "PRESENTER CREATED");

    }

    private ArrayList<String> initTitles() {
        return Constants.getTitles();
    }



    @Override
    public void onViewClicked(int position) {
        if (mainPresenter != null){
            String title = initTitles().get(position);
            mainPresenter.replaceFragment(DetailsFragment.getInstance(title, mainPresenter));
            mainPresenter.showHomeButton(true, title);
        }
    }

    @Override
    public void onCreateView() {
        if (adapter != null)
            fragmentView.setAdapter(adapter);
    }

    @Override
    public void onSelect(String link, Context context) {
        context.startActivity(new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(link))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
