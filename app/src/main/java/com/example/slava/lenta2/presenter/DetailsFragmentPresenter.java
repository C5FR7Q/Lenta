package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.other.DataListMapper;
import com.example.slava.lenta2.other.IPostExecuteSchedulerProvider;
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
	private final IPostExecuteSchedulerProvider postExecuteSchedulerProvider;
	private CompositeDisposable disposables;

	public
	DetailsFragmentPresenter(final IDetailsFragmentView detailsFragmentView,
	                         final IMainActivityPresenter mainPresenter,
	                         final String title,
	                         final ITitlesClient titlesClient,
	                         final LentaClient lentaClient,
	                         final IPostExecuteSchedulerProvider postExecuteSchedulerProvider) {
		this.detailsFragmentView = detailsFragmentView;
		this.mainPresenter = mainPresenter;
		this.title = title;
		this.titlesClient = titlesClient;
		this.lentaClient = lentaClient;
		this.postExecuteSchedulerProvider = postExecuteSchedulerProvider;
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
		if (view.hasInternetConnection()) {
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
		if (!detailsFragmentView.hasInternetConnection()) {
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
