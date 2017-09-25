package com.example.slava.lenta2.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.slava.lenta2.model.data_client.Data;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.view.adapters.RvAdapterItem;
import com.example.slava.lenta2.view.adapters.RvAdapterMain;
import com.example.slava.lenta2.view.fragment.DetailsFragment;
import com.example.slava.lenta2.view.fragment.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by slava on 06.09.2017.
 */

public class MainFragmentPresenter implements IMainFragmentPresenter {
    private RvAdapterMain adapter;
    private IMainFragmentView fragmentView;
    private IMainPresenter mainPresenter;
    private ITitlesClient titlesClient;
    private LentaClient lentaClient;
    private CompositeDisposable disposables = new CompositeDisposable();

    public MainFragmentPresenter(IMainFragmentView fragmentView,
                                 IMainPresenter mainPresenter,
                                 ITitlesClient titlesClient,
                                 LentaClient lentaClient) {
        this.fragmentView = fragmentView;
        this.mainPresenter = mainPresenter;
        this.titlesClient = titlesClient;
        this.lentaClient = lentaClient;
        ArrayList<String> titles = initTitles();
        RvAdapterItem.OnItemSelectedListener listener = this::onSelect;
        adapter = new RvAdapterMain(titles, this, listener);
    }

    private ArrayList<String> initTitles() {
        return titlesClient.getTitles();
    }

    @Override
    public void onViewClicked(int position) {
        if (mainPresenter != null){
            String title = initTitles().get(position);
            mainPresenter.replaceFragment(DetailsFragment.getInstance(title, mainPresenter), true);
            mainPresenter.showHomeButton(true, title);
        }
    }

    @Override
    public void onCreateView() {
        if (adapter != null)
            fragmentView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
    }

    @Override
    public void askDatas(RvAdapterMain.ViewHolder holder,
                         int position,
                         boolean includeDesc,
                         RvAdapterItem.OnItemSelectedListener insideListener) {
        disposables.add(lentaClient.makeMagic(position, initConsumer(holder, includeDesc, insideListener)));
    }

    @NonNull
    private Consumer<List<Data>> initConsumer(RvAdapterMain.ViewHolder holder,
                                              boolean includeDesc,
                                              RvAdapterItem.OnItemSelectedListener insideListener) {
        return dataz -> {
            adapter.setInsideAdapter(holder, dataz, includeDesc, insideListener);
            adapter.addDatas(dataz);
        };
    }

    private void onSelect(String link, Context context) {
        context.startActivity(new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(link))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
