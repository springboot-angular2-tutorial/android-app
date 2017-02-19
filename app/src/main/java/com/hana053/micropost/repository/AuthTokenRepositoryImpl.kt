package com.hana053.micropost.repository

import android.content.SharedPreferences

class AuthTokenRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : AuthTokenRepository {

    private val AUTH_TOKEN = "AUTH_TOKEN"

    override fun get(): String? {
        return sharedPreferences.getString(AUTH_TOKEN, null)
    }

    override fun set(authToken: String) {
        sharedPreferences.edit()
            .putString(AUTH_TOKEN, authToken)
            .apply()
    }

    override fun clear() {
        sharedPreferences.edit()
            .putString(AUTH_TOKEN, null)
            .apply()
    }

}