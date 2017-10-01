package com.example.slava.lenta2.presenter;

import android.app.Fragment;
import android.os.Parcelable;

import com.example.slava.lenta2.view.activity.IMainView;

/**
 * Created by slava on 06.09.2017.
 */

public interface IMainActivityPresenter extends Parcelable{
    void onResume();
    void onCreate(IMainView view);
    void onDestroy();
    void onBackPressed();
    void replaceFragment(Fragment fragment, boolean addToBackStack);
    void showProgressDialog();
    void hideProgressDialog();
    void showHomeButton(boolean isShowing, String title);
}
