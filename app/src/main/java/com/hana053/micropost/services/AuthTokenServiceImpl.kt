package com.hana053.micropost.services

import android.content.SharedPreferences
import com.hana053.micropost.domain.User
import com.nimbusds.jose.JWSObject
import java.text.ParseException


class AuthTokenServiceImpl(
    private val sharedPreferences: SharedPreferences
) : AuthTokenService {

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

    override fun isMyself(user: User): Boolean {
        if (getAuthToken() == null) return false
        try {
            val jwsObject = JWSObject.parse(getAuthToken())
            val sub = jwsObject.payload.toJSONObject()["sub"].toString()
            return user.id == java.lang.Long.valueOf(sub)
        } catch (e: ParseException) {
            throw RuntimeException(e)
        }

    }
}