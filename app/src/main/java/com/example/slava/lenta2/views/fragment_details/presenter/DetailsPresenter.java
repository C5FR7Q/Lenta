package com.example.slava.lenta2.views.fragment_details.presenter;

import com.example.slava.lenta2.Constants;
import com.example.slava.lenta2.adapters.RvAdapterItem;
import com.example.slava.lenta2.client.LentaClient;
import com.example.slava.lenta2.views.activity.presenter.IMainPresenter;
import com.example.slava.lenta2.views.fragment_details.view.IDetailsFragmentView;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by slava on 07.09.2017.
 */

public class DetailsPresenter implements IDetailsPresenter {
    private IDetailsFragmentView detailsFragmentView;
    private IMainPresenter mainPresenter;

    public DetailsPresenter(IDetailsFragmentView detailsFragmentView, IMainPresenter mainPresenter, String titile) {
        this.detailsFragmentView = detailsFragmentView;
        this.mainPresenter = mainPresenter;

        mainPresenter.showProgressDialog();
        for (int i = 0; i < Constants.getTitles().size(); i++){
            if (Constants.getTitles().get(i).equals(titile))
                LentaClient.getInstance()
                        .get(i)
                        .subscribeOn(Schedulers.io())
                        .subscribe(datas -> {
                           mainPresenter.hideProgressDialog();
                            detailsFragmentView.setAdapter(new RvAdapterItem(datas));
                        });
        }
    }
}
