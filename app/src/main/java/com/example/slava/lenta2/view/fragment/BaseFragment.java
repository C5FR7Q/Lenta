package com.example.slava.lenta2.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

/**
 * Created by slava on 13.10.2017.
 */

public abstract
class BaseFragment
		extends Fragment
		implements IBaseFragmentView
{
	public static final String MSG_NO_INTERNET = "No Internet connection.";
	protected SwipeRefreshLayout refreshLayout;

	@Override
	public
	void setRefreshing(final boolean isRefreshing) {
		refreshLayout.setRefreshing(isRefreshing);
	}

	@Override
	public
	void browse(final String link) {
		getActivity().startActivity(new Intent()
				.setAction(Intent.ACTION_VIEW)
				.setData(Uri.parse(link))
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}

	@Override
	public
	void showMessage(final String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}
}
