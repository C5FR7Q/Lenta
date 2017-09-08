package com.example.slava.lenta2.views.activity.view;

import android.app.Fragment;

/**
 * Created by slava on 06.09.2017.
 */

public interface IMainView {
    void replaceFragment(Fragment fragment);
    void showProgressDialog();
    void hideProgressDialog();
    void showHomeButton(boolean isShowing, String title);
}