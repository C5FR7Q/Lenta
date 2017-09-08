package com.example.slava.lenta2.views.activity.presenter;

import android.app.Fragment;
import android.os.Parcelable;

/**
 * Created by slava on 06.09.2017.
 */

public interface IMainPresenter extends Parcelable{
    void onResume();
    void onDestroy();
    void onBackPressed();
    void replaceFragment(Fragment fragment);
    void showProgressDialog();
    void hideProgressDialog();
    void showHomeButton(boolean isShowing, String title);
}
