package com.hana053.myapp.interactors

import com.hana053.micropost.domain.Micropost
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface FeedInteractor {

    @GET("feed?count=20")
    fun loadNextFeed(@Query("sinceId") sinceId: Long?): Observable<List<Micropost>>

    @GET("feed?count=20")
    fun loadPrevFeed(@Query("maxId") maxId: Long?): Observable<List<Micropost>>
}