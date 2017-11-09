package com.example.slava.lenta2.view.fragment;

import com.example.slava.lenta2.view.Data;
import com.example.slava.lenta2.view.adapters.RvAdapterItem;

import java.util.List;

/**
 * Created by slava on 07.09.2017.
 */

public
interface IDetailsFragmentView
		extends IBaseFragmentView
{

	void showData(List<Data> dataList, RvAdapterItem.OnItemSelectedListener listener);
	void refreshData(List<Data> dataList);
}
