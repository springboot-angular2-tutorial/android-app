package com.hana053.micropost.services;

import android.content.SharedPreferences;

import com.android.annotations.Nullable;
import com.hana053.micropost.domain.User;
import com.nimbusds.jose.JWSObject;

import java.text.ParseException;

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

    public boolean isMyself(User user) {
        if (getAuthToken() == null) return false;
        try {
            final JWSObject jwsObject = JWSObject.parse(getAuthToken());
            final String sub = jwsObject.getPayload().toJSONObject().get("sub").toString();
            return user.getId() == Long.valueOf(sub);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
