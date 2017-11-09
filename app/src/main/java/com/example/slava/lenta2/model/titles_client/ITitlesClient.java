package com.example.slava.lenta2.model.titles_client;

import java.util.ArrayList;

/**
 * Created by slava on 15.09.2017.
 */

public
interface ITitlesClient
{
	//    Здесь,скорее, для реалистичности должен быть Observable<List<String>> вместо ArrayList<String>
	ArrayList<String> getTitles();
}
