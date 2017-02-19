package com.hana053.micropost.interactor

import com.hana053.micropost.domain.RelatedUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable


interface RelatedUserListInteractor {

    @GET("users/{id}/followings")
    fun listFollowings(
        @Path("id") userId: Long,
        @Query("maxId") maxId: Long?
    ): Observable<List<RelatedUser>>

    @GET("users/{id}/followers")
    fun listFollowers(
        @Path("id") userId: Long,
        @Query("maxId") maxId: Long?
    ): Observable<List<RelatedUser>>

}