package com.hana053.micropost.interactors

import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable

interface RelationshipInteractor {

    @POST("relationships/to/{followerId}")
    fun follow(@Path("followerId") followerId: Long): Observable<Void>

    @DELETE("relationships/to/{followerId}")
    fun unfollow(@Path("followerId") followerId: Long): Observable<Void>

}