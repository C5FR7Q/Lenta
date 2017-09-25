package com.example.slava.lenta2.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.slava.lenta2.model.data_client.Data;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.view.adapters.RvAdapterItem;
import com.example.slava.lenta2.view.fragment.IDetailsFragmentView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by slava on 07.09.2017.
 */

public class DetailsFragmentPresenter implements IDetailsFragmentPresenter {
    private final String title;
    private final ITitlesClient titlesClient;
    private final LentaClient lentaClient;
    private final IDetailsFragmentView detailsFragmentView;
    private final IMainPresenter mainPresenter;
    private final Consumer<List<Data>> listConsumer;
    private CompositeDisposable disposables;

    public DetailsFragmentPresenter(IDetailsFragmentView detailsFragmentView,
                                    IMainPresenter mainPresenter,
                                    String title,
                                    ITitlesClient titlesClient,
                                    LentaClient lentaClient) {
        this.detailsFragmentView = detailsFragmentView;
        this.mainPresenter = mainPresenter;
        this.title = title;
        this.titlesClient = titlesClient;
        this.lentaClient = lentaClient;
        this.listConsumer = initConsumer();
        this.disposables = new CompositeDisposable();
    }

    private Consumer<List<Data>> initConsumer() {
        return datas -> {
            mainPresenter.hideProgressDialog();
            detailsFragmentView.setAdapter(new RvAdapterItem(datas, true, true, this));
        };
    }

    public void browse(String link, Context context) {
        context.startActivity(new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(link))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void onCreateView() {
        mainPresenter.showProgressDialog();
        for (int i = 0; i < titlesClient.getTitles().size(); i++){
            if (titlesClient.getTitles().get(i).equals(title))
                disposables.add(lentaClient.makeMagic(i, listConsumer));
        }
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
    }

    @Override
    public void onSelect(String link, Context context) {
        if (link.matches("^https://lenta\\.ru.*$"))
            browse(link, context);
    }

    public CompositeDisposable getDisposables() {
        return disposables;
    }
}
