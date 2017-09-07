package com.example.slava.lenta2.views.activity.presenter;


import android.app.Fragment;

import com.example.slava.lenta2.views.activity.view.IMainView;
import com.example.slava.lenta2.views.fragment_main.view.MainFragment;

/**
 * Created by slava on 06.09.2017.
 */

public class MainPresenter implements IMainPresenter {
    private IMainView mainView;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
        replaceFragment(MainFragment.getInstance(this));
    }

//    Fragments are ready
    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void replaceFragment(Fragment fragment) {
        mainView.replaceFragment(fragment);
    }

    @Override
    public void showProgressDialog() {
        mainView.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        mainView.hideProgressDialog();
    }

    @Override
    public void showHomeButton(boolean isShowing, String title) {
        mainView.showHomeButton(isShowing, title);
    }
}
