package com.example.slava.lenta2.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

/**
 * Created by slava on 13.10.2017.
 */

public abstract class BaseFragment extends Fragment implements IBaseFragmentView {
    protected SwipeRefreshLayout refreshLayout;

    @Override
    public void setRefreshing(boolean isRefreshing) {
        refreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public boolean isRefreshing() {
        return refreshLayout.isRefreshing();
    }

    @Override
    public boolean hasInternetConnection() {
            ConnectivityManager cm =
                    (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void browse(String link) {
        getActivity().startActivity(new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(link))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
    }
}
