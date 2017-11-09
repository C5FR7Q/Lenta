package com.example.slava.lenta2.model.titles_client;

import java.util.ArrayList;

/**
 * Created by slava on 07.09.2017.
 */

public
class TitlesClient
		implements ITitlesClient
{

	public
	TitlesClient() {

	}

	@Override
	public
	ArrayList<String> getTitles() {
		final ArrayList<String> titles = new ArrayList<>();
		titles.add("Hottest news");
		titles.add("Latest news");
		titles.add("All news");
		return titles;
	}
}
