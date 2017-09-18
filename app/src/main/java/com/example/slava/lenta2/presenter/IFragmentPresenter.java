package com.example.slava.lenta2.presenter;

import com.example.slava.lenta2.view.adapters.RvAdapterItem;

/**
 * Created by slava on 06.09.2017.
 */

public interface IFragmentPresenter extends RvAdapterItem.OnItemSelectedListener{
    void onViewClicked(int position);

    void onCreateView();
}
