package com.hana053.micropost.interactors;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface LoginInteractor {

    @POST("auth")
    Observable<LoginResponse> login(@Body LoginRequest request);

    class LoginRequest {
        private final String email;
        private final String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    class LoginResponse {
        public String getToken() {
            return token;
        }

        private final String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }
}
