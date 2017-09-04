package com.example.slava.lenta2.model;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by slava on 29.08.2017.
 */

public interface LentaApi {
    @GET("rss/top7")
    Observable<RssSeed> getHottest();

    @GET("rss/last24")
    Observable<RssSeed> getNewest();

    @GET("rss/news")
    Observable<RssSeed> getAll();
}
