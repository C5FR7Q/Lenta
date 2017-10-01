package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.view.adapters.RvAdapterItem;
import com.example.slava.lenta2.view.adapters.RvAdapterMain;
import com.example.slava.lenta2.view.fragment.IMainFragmentView;

/**
 * Created by slava on 06.09.2017.
 */

public interface IMainFragmentPresenter {
    void onViewClicked(int position);

    void onCreateView(Bundle savedInstanceState, IMainFragmentView view);

    void askDatas(RvAdapterMain.ViewHolder holder,
                  int position,
                  boolean includeDesc,
                  RvAdapterItem.OnItemSelectedListener insideListener);

    void onDestroyView();
}
