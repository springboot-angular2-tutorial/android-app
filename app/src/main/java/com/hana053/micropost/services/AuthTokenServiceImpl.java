package com.hana053.micropost.services;

import android.content.SharedPreferences;

import com.android.annotations.Nullable;

class AuthTokenServiceImpl implements AuthTokenService {
    private static final String AUTH_TOKEN = "AUTH_TOKEN";

    private final SharedPreferences sharedPreferences;

    AuthTokenServiceImpl(SharedPreferences sharedPreferences) {
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
