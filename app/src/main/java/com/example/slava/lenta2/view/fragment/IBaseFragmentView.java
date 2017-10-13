package com.example.slava.lenta2.view.fragment;

/**
 * Created by slava on 11.10.2017.
 */

public interface IBaseFragmentView {
    void setRefreshing(boolean isRefreshing);
    boolean isRefreshing();
    boolean hasInternetConnection();
    void browse(String link);
    void showMessage(String msg);
}
