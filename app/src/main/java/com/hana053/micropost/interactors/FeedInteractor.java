package com.hana053.micropost.interactors;

import com.hana053.micropost.domain.Micropost;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FeedInteractor {

    @GET("feed?count=20")
    Observable<List<Micropost>> loadNextFeed(@Query("sinceId") Long sinceId);

    @GET("feed?count=20")
    Observable<List<Micropost>> loadPrevFeed(@Query("maxId") Long maxId);
}
