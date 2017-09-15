package com.example.slava.lenta2.model.data_client;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by slava on 29.08.2017.
 */

public class LentaClient {
    private static final String BASE_URL = "http://lenta.ru/";
    private static LentaClient client;
    private LentaApi lentaApi;

    private LentaClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        lentaApi = retrofit.create(LentaApi.class);
    }

    public static LentaClient getInstance(){
        if (client != null)
            return client;
        client = new LentaClient();
        return client;
    }

    public Observable<List<Data>> getHottest(){
        return lentaApi
                .getHottest()
                .flatMap(seed -> Observable.just(seed.getData()));
    }

    public Observable<List<Data>> getNewest(){
        return lentaApi
                .getNewest()
                .flatMap(seed -> Observable.just(seed.getData()));
    }

    public Observable<List<Data>> getAll(){
        return lentaApi
                .getAll()
                .flatMap(seed -> Observable.just(seed.getData()));
    }

    public Observable<List<Data>> get(int i){
        switch (i){
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
