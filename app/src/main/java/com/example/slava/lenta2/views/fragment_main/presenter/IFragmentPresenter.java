package com.example.slava.lenta2.views.fragment_main.presenter;

import com.example.slava.lenta2.OnRecyclerViewItemSelected;

/**
 * Created by slava on 06.09.2017.
 */

public interface IFragmentPresenter extends OnRecyclerViewItemSelected{
    void onViewClicked(int position);
}
