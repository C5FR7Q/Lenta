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
class DataRepository
		implements IDataRepository<List<List<Data>>>
{
	private final LentaClient mLentaClient;
	private final ICache mCache;
	private CompositeDisposable compositeDisposable;
	private final DataListMapper mMapper;
	private final ISchedulerProvider mSchedulerProvider;

	public
	DataRepository(final LentaClient lentaClient,
	               final ICache cache,
	               final DataListMapper mapper,
	               final ISchedulerProvider schedulerProvider) {
		mLentaClient = lentaClient;
		mCache = cache;
		mMapper = mapper;
		mSchedulerProvider = schedulerProvider;
	}

	@Override
	public
	void setCompositeDisposable(final CompositeDisposable compositeDisposable) {
		/* uas: I don't think that loading process should interrupt on Presenter destroy. */
		/* Question: if no compositeDisposable here, woun't memory leak appear here? */
		this.compositeDisposable = compositeDisposable;
	}

	@Override
	public
	Observable<List<List<Data>>> getData(final boolean hasInternetConnection) {
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
			/* uas: Correct me if I am wrong, but doesn't that mean that loading from Web is performed twice?
			Once here and the second time where the getData method is called? */
			compositeDisposable.add(result.subscribe(lists -> compositeDisposable.add(mCache.putDataList(lists))));
		} else {
			result = mCache.getDataList();
		}
		return result.subscribeOn(mSchedulerProvider.getScheduler());
	}
}
