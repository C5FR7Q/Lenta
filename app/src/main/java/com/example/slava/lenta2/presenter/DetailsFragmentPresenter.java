package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.other.DataListMapper;
import com.example.slava.lenta2.other.IPostExecuteSchedulerProvider;
import com.example.slava.lenta2.view.fragment.BaseFragment;
import com.example.slava.lenta2.view.fragment.IDetailsFragmentView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by slava on 07.09.2017.
 */

public class DetailsFragmentPresenter implements IDetailsFragmentPresenter {
    private final String title;
    private final ITitlesClient titlesClient;
    private final LentaClient lentaClient;
    private final DataListMapper mapper;
    private IDetailsFragmentView detailsFragmentView;
    private final IMainActivityPresenter mainPresenter;
    private IPostExecuteSchedulerProvider postExecuteSchedulerProvider;
    private CompositeDisposable disposables;

    public DetailsFragmentPresenter(IDetailsFragmentView detailsFragmentView,
                                    IMainActivityPresenter mainPresenter,
                                    String title,
                                    ITitlesClient titlesClient,
                                    LentaClient lentaClient,
                                    IPostExecuteSchedulerProvider postExecuteSchedulerProvider) {
        this.detailsFragmentView = detailsFragmentView;
        this.mainPresenter = mainPresenter;
        this.title = title;
        this.titlesClient = titlesClient;
        this.lentaClient = lentaClient;
        this.postExecuteSchedulerProvider = postExecuteSchedulerProvider;
        this.mapper = new DataListMapper();
    }

    @Override
    public void onSelect(String link) {
        if (link.matches("^https://lenta\\.ru.*$"))
            detailsFragmentView.browse(link);

    }

    @Override
    public void onCreateView(Bundle savedInstanceState, IDetailsFragmentView view) {
        this.disposables = new CompositeDisposable();
        this.detailsFragmentView = view;
        if (view.hasInternetConnection()) {
            mainPresenter.showProgressDialog();
            for (int i = 0; i < titlesClient.getTitles().size(); i++)
                if (titlesClient.getTitles().get(i).equals(title))
                    disposables.add(lentaClient
                            .get(i)
                            .observeOn(postExecuteSchedulerProvider.getScheduler())
                            .map(mapper)
                            .subscribe(datas -> {
                                mainPresenter.hideProgressDialog();
                                detailsFragmentView.showDatas(datas, this);
                            }));
        }
    }

    @Override
    public void onDestroyView() {
        this.detailsFragmentView = null;
        this.disposables.dispose();
        this.disposables = null;
    }

    @Override
    public void refresh() {
        detailsFragmentView.setRefreshing(true);
        if (!detailsFragmentView.hasInternetConnection()){
            detailsFragmentView.showMessage(BaseFragment.MSG_NO_INTERNET);
            detailsFragmentView.setRefreshing(false);
            return;
        }
        for (int i = 0; i < titlesClient.getTitles().size(); i++) {
            if (titlesClient.getTitles().get(i).equals(title))
                disposables.add(lentaClient
                        .get(i)
                        .observeOn(postExecuteSchedulerProvider.getScheduler())
                        .map(mapper)
                        .subscribe(datas -> {
                            detailsFragmentView.refreshDatas(datas);
                            detailsFragmentView.setRefreshing(false);
                        }));
        }
    }
}
