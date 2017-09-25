package com.example.slava.lenta2.presenter;

import com.example.slava.lenta2.view.adapters.RvAdapterItem;
import com.example.slava.lenta2.view.adapters.RvAdapterMain;

/**
 * Created by slava on 06.09.2017.
 */

public interface IMainFragmentPresenter extends IFragmentPresenter{
    void onViewClicked(int position);

    void askDatas(RvAdapterMain.ViewHolder holder,
                  int position,
                  boolean includeDesc,
                  RvAdapterItem.OnItemSelectedListener insideListener);
}
