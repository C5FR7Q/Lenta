package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.view.adapters.RvAdapterItem;
import com.example.slava.lenta2.view.fragment.IDetailsFragmentView;

/**
 * Created by slava on 07.09.2017.
 */

public interface IDetailsFragmentPresenter extends RvAdapterItem.OnItemSelectedListener{
    void onCreateView(Bundle savedInstanceState, IDetailsFragmentView view);

    void onDestroyView();
}
