package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.model.cache.ICache;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.other.IPostExecuteSchedulerProvider;
import com.example.slava.lenta2.repository.IRepository;
import com.example.slava.lenta2.repository.Repository;
import com.example.slava.lenta2.view.Data;
import com.example.slava.lenta2.view.fragment.BaseFragment;
import com.example.slava.lenta2.view.fragment.DetailsFragment;
import com.example.slava.lenta2.view.fragment.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by slava on 06.09.2017.
 */

public
class MainFragmentPresenter
		implements IMainFragmentPresenter
{
	private final ICache cache;
	private IRepository repository;
	private IMainFragmentView fragmentView;
	private final IMainActivityPresenter mainPresenter;
	private final ITitlesClient titlesClient;
	private final LentaClient lentaClient;
	private final IPostExecuteSchedulerProvider postExecuteSchedulerProvider;
	private CompositeDisposable disposables;
	private List<List<Data>> mLists;

	public
	MainFragmentPresenter(final IMainFragmentView fragmentView,
	                      final IMainActivityPresenter mainPresenter,
	                      final ITitlesClient titlesClient,
	                      final LentaClient lentaClient,
	                      final IPostExecuteSchedulerProvider postExecuteSchedulerProvider,
	                      final ICache cache) {
		this.fragmentView = fragmentView;
		this.mainPresenter = mainPresenter;
		this.titlesClient = titlesClient;
		this.lentaClient = lentaClient;
		this.postExecuteSchedulerProvider = postExecuteSchedulerProvider;
		this.cache = cache;
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
	}

	@Override
	public
	void onCreate(final Bundle savedInstanceState) {
		disposables = new CompositeDisposable();
		repository = new Repository(fragmentView.hasInternetConnection(), lentaClient, cache, disposables);

		setViewData(lists -> {
			if (mLists.size() == 3)
			fragmentView.setDataLists(mLists);
		});

	}

	private
	void setViewData(final Consumer<? super List<Data>> task) {
		final PublishSubject<List<Data>> allData = repository.getAllData();
		mLists = new ArrayList<>();
		disposables.add(allData.doOnNext(mLists::add)
				.observeOn(postExecuteSchedulerProvider.getScheduler())
				.subscribe(task));
	}

/*
	private
	void loadFromCache() {
		disposables.add(cache.getDataList()
				.observeOn(postExecuteSchedulerProvider.getScheduler())
				.subscribe((lists) -> fragmentView.setDataLists(lists), throwable -> fragmentView.showMessage(
						"Internet connection is lost. No cache available."
				)));
	}
*/

/*	private
	void sendInternetRequest() {
		final List<List<Data>> lists = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			disposables.add(
					lentaClient
							.get(i)
							.map(mapper)
							.observeOn(postExecuteSchedulerProvider.getScheduler())
							.subscribe((e) -> {
								lists.add(e);

//                            *//*Это, скорее всего, костыль. Были идеи нормальной реализации с использованием
//                            * combineLatest. Но в итоге выглядело как еще больший костыль. При этом
//                             * работало не так, как нужно.*//*
								if (lists.size() == 3) {
									disposables.add(cache.putDataList(lists));
									fragmentView.setDataLists(lists);
								}
							}));
		}
	}*/

	@Override
	public
	void onDestroyView() {
		fragmentView = null;
		disposables.dispose();
		disposables = null;
	}


	/*Если заранее известно, что при pull-to-refresh порядок новостей в категориях не меняется,
	* можно оптимизировать процесс. Для этого в refresh должен передаваться лист адаптера. Далее:
	* для данного презентера для каждой категории сравнивается первый элемент(статья) посредством
	* какого-то идентификатора. Можно, что логично, сравнивать по загаловкам статьи. Если равны,
	* то не вызывается метод adapter.setAllData(...), так как перерисовывать одно и то же смысла
	* не имеет. Для DetailsFragmentPresenter все аналогично, только для его единственной категории. */

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
			if (mLists.size() == 3) {
				fragmentView.setDataLists(mLists);
				fragmentView.setRefreshing(false);
			}
		});

/*
		final List<List<Data>> lists = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			disposables.add(lentaClient
					.get(i)
					.observeOn(postExecuteSchedulerProvider.getScheduler())
					.map(mapper)
					.subscribe(lists::add, throwable -> {
							},
							() -> {
								fragmentView.setRefreshing(false);
								if (lists.size() == 3) {
									disposables.add(cache.putDataList(lists));
									fragmentView.setDataLists(lists);
								}
							}
					));
		}
*/
	}

	@Override
	public
	void onSelect(final String link) {
		if (link.matches("^https://lenta\\.ru.*$")) {
			fragmentView.browse(link);
		}
	}

}
