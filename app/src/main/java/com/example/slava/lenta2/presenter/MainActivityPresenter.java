package com.example.slava.lenta2.presenter;


import android.app.Fragment;
import android.os.Parcel;

import com.example.slava.lenta2.view.activity.IMainView;
import com.example.slava.lenta2.view.fragment.MainFragment;

/**
 * Created by slava on 06.09.2017.
 */

public class MainActivityPresenter implements IMainActivityPresenter {
    private IMainView mainView;

    public MainActivityPresenter(IMainView mainView) {
        this.mainView = mainView;
        replaceFragment(MainFragment.getInstance(this), false);
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
        mainView.showHomeButton(false, "Lenta");
    }

    @Override
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        mainView.replaceFragment(fragment, addToBackStack);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
