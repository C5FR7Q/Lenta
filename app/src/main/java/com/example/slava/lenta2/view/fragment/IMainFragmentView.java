package com.example.slava.lenta2.view.fragment;

import com.example.slava.lenta2.view.Data;

import java.util.List;

/**
 * Created by slava on 06.09.2017.
 */

public
interface IMainFragmentView
		extends IBaseFragmentView
{
	void setDataLists(List<List<Data>> lists);
}
