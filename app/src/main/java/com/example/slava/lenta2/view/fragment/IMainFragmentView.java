package com.example.slava.lenta2.view.fragment;

import android.support.v7.widget.RecyclerView;

/**
 * Created by slava on 06.09.2017.
 */

public interface IMainFragmentView extends IRefreshingView{
    void setAdapter(RecyclerView.Adapter adapter);

    void browse(String link);
}
