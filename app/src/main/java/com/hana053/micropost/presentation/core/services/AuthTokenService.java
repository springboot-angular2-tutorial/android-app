package com.hana053.micropost.presentation.core.services;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthTokenService {

    private static final String AUTH_TOKEN = "AUTH_TOKEN";

    private final SharedPreferences sharedPreferences;

    @Inject
    AuthTokenService(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Nullable
    public String getAuthToken() {
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }

    public void setAuthToken(String authToken) {
        sharedPreferences.edit()
                .putString(AUTH_TOKEN, authToken)
                .apply();
    }

    public void clearAuthToken() {
        sharedPreferences.edit()
                .putString(AUTH_TOKEN, null)
                .apply();
    }

}
