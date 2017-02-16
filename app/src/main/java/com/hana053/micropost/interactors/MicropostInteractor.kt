package com.hana053.micropost.interactors

import com.hana053.micropost.domain.Micropost
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable


interface MicropostInteractor {

    @POST("microposts")
    fun create(@Body request: MicropostRequest): Observable<Micropost>

    data class MicropostRequest(val content: String)
}