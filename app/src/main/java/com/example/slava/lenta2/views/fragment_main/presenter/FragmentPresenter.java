package com.example.slava.lenta2.views.fragment_main.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.slava.lenta2.adapters.RvAdapterMain;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.model.titles_client.TitlesClient;
import com.example.slava.lenta2.views.activity.presenter.IMainPresenter;
import com.example.slava.lenta2.views.fragment_details.view.DetailsFragment;
import com.example.slava.lenta2.views.fragment_main.view.IFragmentView;

import java.util.ArrayList;

/**
 * Created by slava on 06.09.2017.
 */

public class FragmentPresenter implements IFragmentPresenter{
    private RvAdapterMain adapter;
    private IFragmentView fragmentView;
    private IMainPresenter mainPresenter;
    private ITitlesClient titlesClient;

    public FragmentPresenter(IFragmentView fragmentView,
                             IMainPresenter mainPresenter,
                             ITitlesClient titlesClient,
                             LentaClient lentaClient) {
        this.fragmentView = fragmentView;
        this.mainPresenter = mainPresenter;
        this.titlesClient = titlesClient;
        ArrayList<String> titles = initTitles();
        adapter = new RvAdapterMain(titles, this, this, new TitlesClient(), lentaClient);
    }

    private ArrayList<String> initTitles() {
        return titlesClient.getTitles();
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
