package com.hana053.micropost.interactors

import com.hana053.micropost.domain.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable

interface UserInteractor {

    @GET("users/{id}")
    fun get(@Path("id") userId: Long): Observable<User>

    @POST("users")
    fun create(@Body request: SignupRequest): Observable<User>

    data class SignupRequest(
        val name: String,
        val email: String,
        val password: String
    )
}

