package com.example.slava.lenta2.other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by vva on 13/11/2017.
 */

public
class NetworkStateProvider
{
	private static ConnectivityManager sCm;
	private static NetworkStateProvider instance;
	private
	NetworkStateProvider() {

	}

	public static
	void init(final Context context) {
		instance = new NetworkStateProvider();
		sCm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public static
	NetworkStateProvider getInstance() {
		return instance;
	}

	public
	boolean hasInternetConnection() {
		NetworkInfo sNetInfo = null;
		if (sCm != null) {
			sNetInfo = sCm.getActiveNetworkInfo();
		}
		return sNetInfo != null && sNetInfo.isConnectedOrConnecting();
	}


}
