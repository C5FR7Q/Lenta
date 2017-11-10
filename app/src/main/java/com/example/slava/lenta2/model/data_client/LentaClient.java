package com.example.slava.lenta2.model.data_client;

import com.example.slava.lenta2.other.ISchedulerProvider;
import com.example.slava.lenta2.other.PreExecuteSchedulerProvider;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by slava on 29.08.2017.
 */

public
class LentaClient
{
	private static final String BASE_URL = "http://lenta.ru/";
	private static LentaClient client;
	private final LentaApi lentaApi;
	private final ISchedulerProvider mSchedulerProvider;

	private
	LentaClient(final ISchedulerProvider schedulerProvider) {
		final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(SimpleXmlConverterFactory.create())
				.build();
		lentaApi = retrofit.create(LentaApi.class);
		mSchedulerProvider = schedulerProvider;
	}

	public static
	LentaClient getInstance() {
		if (client != null) {
			return client;
		}
		client = new LentaClient(new PreExecuteSchedulerProvider());
		return client;
	}

	private
	Observable<List<DataDTO>> getHottest() {
		return lentaApi
				.getHottest()
				.flatMap(seed -> Observable.just(seed.getData()))
				.subscribeOn(mSchedulerProvider.getScheduler());
	}

	private
	Observable<List<DataDTO>> getNewest() {
		return lentaApi
				.getNewest()
				.flatMap(seed -> Observable.just(seed.getData()))
				.subscribeOn(mSchedulerProvider.getScheduler());
	}

	private
	Observable<List<DataDTO>> getAll() {
		return lentaApi
				.getAll()
				.flatMap(seed -> Observable.just(seed.getData()))
				.subscribeOn(mSchedulerProvider.getScheduler());
	}

	public
	Observable<List<DataDTO>> get(final int i) {
		switch (i) {
			case 0:
				return getHottest();
			case 1:
				return getNewest();
			case 2:
				return getAll();
		}
		return null;
	}

	public
	Observable<List<List<DataDTO>>> getLists() {
		final ArrayList<Observable<List<DataDTO>>> observables = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			observables.add(get(i));
		}
		return Observable.zip(observables, objects -> {
			final List<List<DataDTO>> lists = new ArrayList<>();
			for (Object o : objects) {
				//noinspection unchecked
				lists.add((List<DataDTO>) o);
			}
			return lists;
		}).subscribeOn(mSchedulerProvider.getScheduler());
	}

}
