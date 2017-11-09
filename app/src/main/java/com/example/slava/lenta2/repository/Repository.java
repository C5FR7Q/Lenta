package com.example.slava.lenta2.repository;

import com.example.slava.lenta2.model.cache.ICache;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.other.DataListMapper;
import com.example.slava.lenta2.view.Data;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by vva on 09/11/2017.
 */

public
class Repository
		implements IRepository
{
	private final
	boolean hasInternetConnection;
	private final
	LentaClient mLentaClient;
	private final
	ICache mCache;
	private final CompositeDisposable compositeDisposable;

	public
	Repository(final boolean hasInternetConnection,
	           final LentaClient lentaClient,
	           final ICache cache,
	           final CompositeDisposable compositeDisposable) {
		this.hasInternetConnection = hasInternetConnection;
		this.compositeDisposable = compositeDisposable;
		mLentaClient = lentaClient;
		mCache = cache;
	}

	@Override
	public
	PublishSubject<List<Data>> getAllData() {
		final PublishSubject<List<Data>> subject = PublishSubject.create();
		if (hasInternetConnection) {
			final List<List<Data>> lists = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				compositeDisposable.add(mLentaClient.get(i)
						.map(new DataListMapper())
						.subscribeOn(Schedulers.io())
						.subscribe(e -> {
							subject.onNext(e);

							if (lists.size() == 3) {
								final Disposable d = mCache.putDataList(lists);
								if (d != null) {
									compositeDisposable.add(d);
								}
							}
						}));
			}
		} else {
			mCache.getDataList().forEach(lists -> {
				for (List<Data> list: lists){
					subject.onNext(list);
				}
			});
		}
		return subject;
	}
}
