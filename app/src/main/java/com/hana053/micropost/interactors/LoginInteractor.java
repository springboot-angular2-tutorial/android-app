package com.hana053.micropost.interactors;

import lombok.Value;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface LoginInteractor {

    @POST("login")
    Observable<Response<Void>> login(@Body LoginRequest request);

    @Value
    class LoginRequest {
        private final String email;
        private final String password;
    }
}
