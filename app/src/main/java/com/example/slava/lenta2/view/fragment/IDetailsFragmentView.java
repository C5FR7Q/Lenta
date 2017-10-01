package com.example.slava.lenta2.view.fragment;

import com.example.slava.lenta2.model.data_client.Data;
import com.example.slava.lenta2.view.adapters.RvAdapterItem;

import java.util.List;

/**
 * Created by slava on 07.09.2017.
 */

public interface IDetailsFragmentView {

    void showDatas(List<Data> datas, RvAdapterItem.OnItemSelectedListener listener);

    void browse(String link);
}
