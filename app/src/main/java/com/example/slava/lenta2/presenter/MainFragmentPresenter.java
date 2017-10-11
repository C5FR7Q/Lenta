package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.model.data_client.Data;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.other.SchedulersProvider;
import com.example.slava.lenta2.view.adapters.RvAdapterItem;
import com.example.slava.lenta2.view.adapters.RvAdapterMain;
import com.example.slava.lenta2.view.fragment.DetailsFragment;
import com.example.slava.lenta2.view.fragment.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slava on 06.09.2017.
 */

public class MainFragmentPresenter implements IMainFragmentPresenter {
    private RvAdapterMain adapter;
    private IMainFragmentView fragmentView;
    private IMainActivityPresenter mainPresenter;
    private ITitlesClient titlesClient;
    private LentaClient lentaClient;
    private SchedulersProvider schedulers;

    public MainFragmentPresenter(IMainFragmentView fragmentView,
                                 IMainActivityPresenter mainPresenter,
                                 ITitlesClient titlesClient,
                                 LentaClient lentaClient,
                                 SchedulersProvider schedulers) {
        this.fragmentView = fragmentView;
        this.mainPresenter = mainPresenter;
        this.titlesClient = titlesClient;
        this.lentaClient = lentaClient;
        this.schedulers = schedulers;
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
    public void onCreateView(Bundle savedInstanceState, IMainFragmentView view) {
        this.fragmentView = view;
        if (adapter != null)
            fragmentView.setAdapter(adapter);
    }

    @Override
    public void askDatas(RvAdapterMain.ViewHolder holder, int position, boolean includeDesc, RvAdapterItem.OnItemSelectedListener insideListener) {
        lentaClient
                .get(position)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe(dataz -> {
                    adapter.setInsideAdapter(holder, dataz, includeDesc, insideListener);
                    adapter.addDatas(dataz);
                });
    }

    @Override
    public void onDestroyView() {
        this.fragmentView = null;
    }

    @Override
    public void refresh() {
        fragmentView.setRefreshing(true);
        List<List<Data>> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            lentaClient
                    .get(i)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.mainThread())
                    .subscribe(datas::add, throwable -> {},
                            () ->{
                                fragmentView.setRefreshing(false);
                                adapter.setAllDatas(datas);
                            });
    }

    private void onSelect(String link) {
        if (link.matches("^https://lenta\\.ru.*$"))
            fragmentView.browse(link);
    }
}
