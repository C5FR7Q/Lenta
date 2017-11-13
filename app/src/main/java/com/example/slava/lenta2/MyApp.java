package com.example.slava.lenta2;

import android.app.Application;

import com.example.slava.lenta2.other.NetworkStateProvider;
import com.example.slava.lenta2.other.RepositoryProvider;

/**
 * Created by vva on 13/11/2017.
 */

public
class MyApp
		extends Application
{
	@Override
	public
	void onCreate() {
		super.onCreate();
		RepositoryProvider.init(this);
		NetworkStateProvider.init(this);
	}
}
