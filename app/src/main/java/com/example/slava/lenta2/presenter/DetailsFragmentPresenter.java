package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.other.DataListMapper;
import com.example.slava.lenta2.other.ISchedulerProvider;
import com.example.slava.lenta2.other.NetworkStateProvider;
import com.example.slava.lenta2.view.fragment.BaseFragment;
import com.example.slava.lenta2.view.fragment.IDetailsFragmentView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by slava on 07.09.2017.
 */

public
class DetailsFragmentPresenter
		implements IDetailsFragmentPresenter
{
	private final String title;
	private final ITitlesClient titlesClient;
	private final LentaClient lentaClient;
	private final DataListMapper mapper;
	private IDetailsFragmentView detailsFragmentView;
	private final IMainActivityPresenter mainPresenter;
	private final ISchedulerProvider postExecuteSchedulerProvider;
	private CompositeDisposable disposables;
	private final NetworkStateProvider mNetworkStateProvider;

	public
	DetailsFragmentPresenter(final IDetailsFragmentView detailsFragmentView,
	                         final IMainActivityPresenter mainPresenter,
	                         final String title,
	                         final ITitlesClient titlesClient,
	                         final LentaClient lentaClient,
	                         final ISchedulerProvider postExecuteSchedulerProvider,
	                         final NetworkStateProvider networkStateProvider) {
		this.detailsFragmentView = detailsFragmentView;
		this.mainPresenter = mainPresenter;
		this.title = title;
		this.titlesClient = titlesClient;
		this.lentaClient = lentaClient;
		this.postExecuteSchedulerProvider = postExecuteSchedulerProvider;
		mNetworkStateProvider = networkStateProvider;
		mapper = new DataListMapper();
	}

	@Override
	public
	void onSelect(final String link) {
		if (link.matches("^https://lenta\\.ru.*$")) {
			detailsFragmentView.browse(link);
		}

	}

	@Override
	public
	void onCreateView(final Bundle savedInstanceState, final IDetailsFragmentView view) {
		disposables = new CompositeDisposable();
		detailsFragmentView = view;
		if (mNetworkStateProvider.hasInternetConnection()) {
			mainPresenter.showProgressDialog();
			for (int i = 0; i < titlesClient.getTitles().size(); i++) {
				if (titlesClient.getTitles().get(i).equals(title)) {
					disposables.add(lentaClient
							.get(i)
							.observeOn(postExecuteSchedulerProvider.getScheduler())
							.map(mapper)
							.subscribe(dataList -> {
								mainPresenter.hideProgressDialog();
								detailsFragmentView.showData(dataList, this);
							}));
				}
			}
		}
	}

	@Override
	public
	void onDestroyView() {
		detailsFragmentView = null;
		disposables.dispose();
		disposables = null;
	}

	@Override
	public
	void refresh() {
		detailsFragmentView.setRefreshing(true);
		if (!mNetworkStateProvider.hasInternetConnection()) {
			detailsFragmentView.showMessage(BaseFragment.MSG_NO_INTERNET);
			detailsFragmentView.setRefreshing(false);
			return;
		}
		for (int i = 0; i < titlesClient.getTitles().size(); i++) {
			if (titlesClient.getTitles().get(i).equals(title))
				disposables.add(lentaClient
						.get(i)
						.observeOn(postExecuteSchedulerProvider.getScheduler())
						.map(mapper)
						.subscribe(dataList -> {
							detailsFragmentView.refreshData(dataList);
							detailsFragmentView.setRefreshing(false);
						}));
		}
	}
}
