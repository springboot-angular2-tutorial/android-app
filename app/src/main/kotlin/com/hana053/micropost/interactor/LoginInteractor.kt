package com.hana053.micropost.interactor

import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface LoginInteractor {

    @POST("auth")
    fun login(@Body request: LoginRequest): Observable<LoginResponse>

    data class LoginRequest(val email: String, val password: String)
    data class LoginResponse(val token: String)

}
