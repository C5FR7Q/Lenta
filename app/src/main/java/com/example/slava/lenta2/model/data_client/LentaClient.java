package com.example.slava.lenta2.model.data_client;

import com.example.slava.lenta2.other.IPreExecuteSchedulerProvider;
import com.example.slava.lenta2.other.PreExecuteSchedulerProvider;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

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
	private final IPreExecuteSchedulerProvider schedulerProvider;

	private
	LentaClient(final IPreExecuteSchedulerProvider schedulerProvider) {
		final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(SimpleXmlConverterFactory.create())
				.build();
		lentaApi = retrofit.create(LentaApi.class);
		this.schedulerProvider = schedulerProvider;
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
				.subscribeOn(schedulerProvider.getScheduler());
	}

	private
	Observable<List<DataDTO>> getNewest() {
		return lentaApi
				.getNewest()
				.flatMap(seed -> Observable.just(seed.getData()))
				.subscribeOn(schedulerProvider.getScheduler());
	}

	private
	Observable<List<DataDTO>> getAll() {
		return lentaApi
				.getAll()
				.flatMap(seed -> Observable.just(seed.getData()))
				.subscribeOn(schedulerProvider.getScheduler());
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

}
