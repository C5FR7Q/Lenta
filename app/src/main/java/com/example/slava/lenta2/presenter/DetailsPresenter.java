package com.example.slava.lenta2.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.slava.lenta2.view.adapters.RvAdapterItem;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.view.fragment.IDetailsFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by slava on 07.09.2017.
 */

public class DetailsPresenter implements IDetailsPresenter {
    private IDetailsFragmentView detailsFragmentView;
    private IMainPresenter mainPresenter;

    public DetailsPresenter(IDetailsFragmentView detailsFragmentView,
                            IMainPresenter mainPresenter,
                            String titile,
                            ITitlesClient titlesClient,
                            LentaClient lentaClient) {
        this.detailsFragmentView = detailsFragmentView;
        this.mainPresenter = mainPresenter;

        mainPresenter.showProgressDialog();
        for (int i = 0; i < titlesClient.getTitles().size(); i++){
            if (titlesClient.getTitles().get(i).equals(titile))
                lentaClient
                        .get(i)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(datas -> {
                           mainPresenter.hideProgressDialog();
                            detailsFragmentView.setAdapter(new RvAdapterItem(datas, true, true, this));
                        });
        }
    }

    @Override
    public void onSelect(String link, Context context) {
        context.startActivity(new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(link))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
