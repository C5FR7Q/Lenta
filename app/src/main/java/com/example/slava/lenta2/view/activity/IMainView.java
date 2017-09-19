package com.example.slava.lenta2.view.activity;

import android.app.Fragment;

/**
 * Created by slava on 06.09.2017.
 */

public interface IMainView {
    void replaceFragment(Fragment fragment, boolean addToBackStack);
    void showProgressDialog();
    void hideProgressDialog();
    void showHomeButton(boolean isShowing, String title);
}
