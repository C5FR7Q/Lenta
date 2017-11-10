package com.example.slava.lenta2.repository;

import com.example.slava.lenta2.model.cache.ICache;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.other.DataListMapper;
import com.example.slava.lenta2.other.ISchedulerProvider;
import com.example.slava.lenta2.view.Data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by vva on 09/11/2017.
 */

public
class Repository
		implements IRepository
{
	private final
	LentaClient mLentaClient;
	private final
	ICache mCache;
	private
	CompositeDisposable compositeDisposable;
	private final
	DataListMapper mMapper;
	private final
	ISchedulerProvider mSchedulerProvider;

	public
	Repository(final LentaClient lentaClient,
	           final ICache cache,
	           final DataListMapper mapper,
	           final ISchedulerProvider schedulerProvider) {
		mLentaClient = lentaClient;
		mCache = cache;
		mMapper = mapper;
		mSchedulerProvider = schedulerProvider;
	}

	public
	void setCompositeDisposable(final CompositeDisposable compositeDisposable) {
		this.compositeDisposable = compositeDisposable;
	}

	public
	Observable<List<List<Data>>> getAllDataObservable(final boolean hasInternetConnection) {
		final Observable<List<List<Data>>> result;
		if (compositeDisposable == null) {
			return Observable.empty();
		}
		if (hasInternetConnection) {
			result = mLentaClient.getLists()
					.flatMapIterable(lists -> lists)
					.map(mMapper)
					.toList()
					.toObservable();
			compositeDisposable.add(result.subscribe(
					lists -> compositeDisposable.add(mCache.putDataList(lists))
			));
		} else {
			result = mCache.getDataList();
		}
		return result.subscribeOn(mSchedulerProvider.getScheduler());
	}
}
