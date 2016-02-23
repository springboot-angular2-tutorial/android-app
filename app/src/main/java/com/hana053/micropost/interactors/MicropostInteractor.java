package com.hana053.micropost.interactors;

import com.hana053.micropost.domain.Micropost;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface MicropostInteractor {

    @POST("microposts")
    Observable<Micropost> create(@Body Micropost micropost);
}
