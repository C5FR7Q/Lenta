package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.other.ISchedulerProvider;
import com.example.slava.lenta2.repository.IRepository;
import com.example.slava.lenta2.view.Data;
import com.example.slava.lenta2.view.fragment.BaseFragment;
import com.example.slava.lenta2.view.fragment.DetailsFragment;
import com.example.slava.lenta2.view.fragment.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by slava on 06.09.2017.
 */

public
class MainFragmentPresenter
		implements IMainFragmentPresenter
{
	private final IRepository repository;
	private IMainFragmentView fragmentView;
	private final IMainActivityPresenter mainPresenter;
	private final ITitlesClient titlesClient;
	private final ISchedulerProvider postExecuteSchedulerProvider;
	private CompositeDisposable disposables;

	public
	MainFragmentPresenter(final IMainFragmentView fragmentView,
	                      final IMainActivityPresenter mainPresenter,
	                      final ITitlesClient titlesClient,
	                      final ISchedulerProvider postExecuteSchedulerProvider,
	                      final IRepository repository) {
		this.fragmentView = fragmentView;
		this.mainPresenter = mainPresenter;
		this.titlesClient = titlesClient;
		this.postExecuteSchedulerProvider = postExecuteSchedulerProvider;
		this.repository = repository;
		disposables = new CompositeDisposable();
	}

	private
	ArrayList<String> initTitles() {
		return titlesClient.getTitles();
	}

	@Override
	public
	void onViewClicked(final int position) {
		if (!fragmentView.hasInternetConnection()) {
			fragmentView.showMessage(BaseFragment.MSG_NO_INTERNET);
			return;
		}
		if (mainPresenter != null) {
			final String title = initTitles().get(position);
			mainPresenter.replaceFragment(DetailsFragment.getInstance(title, mainPresenter), true);
			mainPresenter.showHomeButton(true, title);
		}
	}

	@Override
	public
	void onCreateView(final Bundle savedInstanceState, final IMainFragmentView view) {
		fragmentView = view;
		disposables = new CompositeDisposable();
		repository.setCompositeDisposable(disposables);

		setViewData(lists -> fragmentView.setDataLists(lists));
	}

	@Override
	public
	void onCreate(final Bundle savedInstanceState) {

	}

	private
	void setViewData(final Consumer<? super List<List<Data>>> task) {
		final Observable<List<List<Data>>> allDataObservable = repository.getAllDataObservable(fragmentView.hasInternetConnection());
		disposables.add(allDataObservable.observeOn(postExecuteSchedulerProvider.getScheduler())
				.subscribe(task));
	}

	@Override
	public
	void onDestroyView() {
		fragmentView = null;
		disposables.dispose();
		disposables = null;
	}

	@Override
	public
	void refresh() {
		fragmentView.setRefreshing(true);
		if (!fragmentView.hasInternetConnection()) {
			fragmentView.showMessage(BaseFragment.MSG_NO_INTERNET);
			fragmentView.setRefreshing(false);
			return;
		}

		setViewData(lists -> {
			fragmentView.setDataLists(lists);
			fragmentView.setRefreshing(false);
		});

	}

	@Override
	public
	void onSelect(final String link) {
		if (link.matches("^https://lenta\\.ru.*$")) {
			fragmentView.browse(link);
		}
	}

}
