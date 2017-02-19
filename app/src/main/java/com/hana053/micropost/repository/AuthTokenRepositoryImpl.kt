package com.hana053.micropost.repository

import android.content.SharedPreferences

class AuthTokenRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : AuthTokenRepository {

    private val AUTH_TOKEN = "AUTH_TOKEN"

    override fun getAuthToken(): String? {
        return sharedPreferences.getString(AUTH_TOKEN, null)
    }

    override fun setAuthToken(authToken: String) {
        sharedPreferences.edit()
            .putString(AUTH_TOKEN, authToken)
            .apply()
    }

    override fun clearAuthToken() {
        sharedPreferences.edit()
            .putString(AUTH_TOKEN, null)
            .apply()
    }

}