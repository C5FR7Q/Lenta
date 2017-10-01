package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.other.SchedulersProvider;
import com.example.slava.lenta2.view.fragment.IDetailsFragmentView;

/**
 * Created by slava on 07.09.2017.
 */

public class DetailsFragmentPresenter implements IDetailsFragmentPresenter {
    private final String title;
    private final ITitlesClient titlesClient;
    private final LentaClient lentaClient;
    private IDetailsFragmentView detailsFragmentView;
    private final IMainActivityPresenter mainPresenter;
    private SchedulersProvider schedulers;

    public DetailsFragmentPresenter(IDetailsFragmentView detailsFragmentView,
                                    IMainActivityPresenter mainPresenter,
                                    String title,
                                    ITitlesClient titlesClient,
                                    LentaClient lentaClient,
                                    SchedulersProvider schedulers) {
        this.detailsFragmentView = detailsFragmentView;
        this.mainPresenter = mainPresenter;
        this.title = title;
        this.titlesClient = titlesClient;
        this.lentaClient = lentaClient;
        this.schedulers = schedulers;
    }

    @Override
    public void onSelect(String link) {
        if (link.matches("^https://lenta\\.ru.*$"))
            detailsFragmentView.browse(link);

    }

    @Override
    public void onCreateView(Bundle savedInstanceState, IDetailsFragmentView view) {
        this.detailsFragmentView = view;
        mainPresenter.showProgressDialog();
        for (int i = 0; i < titlesClient.getTitles().size(); i++){
            if (titlesClient.getTitles().get(i).equals(title))
                lentaClient
                        .get(i)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.mainThread())
                        .subscribe(datas -> {
                            mainPresenter.hideProgressDialog();
                            detailsFragmentView.showDatas(datas, this);
                        });
        }
    }

    @Override
    public void onDestroyView() {
        this.detailsFragmentView = null;
    }
}
