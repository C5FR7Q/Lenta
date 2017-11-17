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
	private boolean mDataLoading = false;

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
	}

	@Override
	public
	Observable<List<List<Data>>> getData(final boolean hasInternetConnection) {
		if (!mDataLoading) {
			final boolean isSkipping;
			isSkipping = mBehaviorSubject.getValue() != null;
			loadData(hasInternetConnection);
			if (isSkipping){
				return mBehaviorSubject.skip(1).hide();
			}
		}
		return mBehaviorSubject.hide();
	}

	private void loadData(final boolean hasInternetConnection){
		mDataLoading = true;
		final Observable<List<List<Data>>> result;
		if (hasInternetConnection) {
			result = mLentaClient.getLists()
					.flatMapIterable(lists -> lists)
					.map(mMapper)
					.toList()
					.toObservable()
					.doOnNext(lists -> {
						mCache.putDataList(lists);
						mBehaviorSubject.onNext(lists);
					});

		} else {
			final List<List<Data>> value = mBehaviorSubject.getValue();
			if (value != null) {
				result = Observable.create(e -> {
					e.onNext(value);
					e.onComplete();
				});
			} else {
				result = mCache.getDataList().doOnNext(mBehaviorSubject::onNext);
//				result.;
//				mBehaviorSubject.onNext(result.blockingFirst());
			}
		}

		result.subscribeOn(mSchedulerProvider.getScheduler()).subscribe(lists -> {
			mDataLoading = false;
		});

	}
}
