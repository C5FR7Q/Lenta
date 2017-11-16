package com.example.slava.lenta2.repository;

import android.util.Log;

import com.example.slava.lenta2.model.cache.ICache;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.other.DataListMapper;
import com.example.slava.lenta2.other.ISchedulerProvider;
import com.example.slava.lenta2.view.Data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by vva on 09/11/2017.
 */
public
class DataRepository
		implements IDataRepository<List<List<Data>>>
{
	private final LentaClient mLentaClient;
	private final ICache mCache;
	private final DataListMapper mMapper;
	private final ISchedulerProvider mSchedulerProvider;
	private final
	BehaviorSubject<List<List<Data>>> mBehaviorSubject;
	/*A little bit time and I've understood, that Subject isn't necessary there by next reasons:
	* 1)I supposed it can influence on putting data to cache. wrong.
	* If app had been closed before data written to cache, it wouldn't have helped anyway
	* 2)It would be necessary ideally, but getData with false hasInternetConnection can be called only once. After initial setViewData
	* in MainFragmentPresenter it won't be possible to getData from cache.*/

	public
	DataRepository(final LentaClient lentaClient,
	               final ICache cache,
	               final DataListMapper mapper,
	               final ISchedulerProvider schedulerProvider) {
		mLentaClient = lentaClient;
		mCache = cache;
		mMapper = mapper;
		mSchedulerProvider = schedulerProvider;
		mBehaviorSubject = BehaviorSubject.create();
		mBehaviorSubject.subscribe(lists -> Log.d("DataRepository", lists.get(0).get(0).getTitle()));
	}

	@Override
	public
	Observable<List<List<Data>>> getData(final boolean hasInternetConnection) {
		final Observable<List<List<Data>>> result;
		if (hasInternetConnection) {
			result = mLentaClient.getLists()
					.flatMapIterable(lists -> lists)
					.map(mMapper)
					.toList()
					.toObservable();
			result.subscribe(data -> {
				mCache.putDataList(data);
				mBehaviorSubject.onNext(data);
			});
		} else {
			final List<List<Data>> value = mBehaviorSubject.getValue();
			if (value != null){
				result = Observable.just(value);
			}
			else {
				result = mCache.getDataList();
				result.subscribe(mBehaviorSubject::onNext);
			}
		}
		return result.subscribeOn(mSchedulerProvider.getScheduler());
	}
}
