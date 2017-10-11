package com.example.slava.lenta2.view.fragment;

import com.example.slava.lenta2.model.data_client.Data;

import java.util.List;

/**
 * Created by slava on 06.09.2017.
 */

public interface IMainFragmentView extends IRefreshingView{
    void browse(String link);
    void setDatas(List<List<Data>> datas);
}
