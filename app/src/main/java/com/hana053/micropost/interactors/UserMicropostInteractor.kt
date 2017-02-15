package com.hana053.micropost.interactors

import com.hana053.micropost.domain.Micropost
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable


interface UserMicropostInteractor {

    @GET("users/{userId}/microposts?count=20")
    fun loadPrevPosts(@Path("userId") userId: Long, @Query("maxId") maxId: Long?): Observable<List<Micropost>>
}