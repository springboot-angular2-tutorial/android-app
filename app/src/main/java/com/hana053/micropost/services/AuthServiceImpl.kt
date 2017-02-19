package com.hana053.micropost.services

import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.domain.User
import com.nimbusds.jose.JWSObject
import java.text.ParseException


class AuthServiceImpl(
    private val authTokenRepository: AuthTokenRepository,
    private val navigator: Navigator
) : AuthService {

    override fun isMyself(user: User): Boolean {
        val authToken = authTokenRepository.getAuthToken() ?: return false
        try {
            val jwsObject = JWSObject.parse(authToken)
            val sub = jwsObject.payload.toJSONObject()["sub"].toString()
            return user.id == java.lang.Long.valueOf(sub)
        } catch (e: ParseException) {
            throw RuntimeException(e)
        }

    }

    override fun logout() {
        authTokenRepository.clearAuthToken()
        navigator.navigateToTop()
    }

    override fun auth(): Boolean {
        if (authTokenRepository.getAuthToken().isNullOrBlank()) {
            logout()
            return false
        }
        return true
    }

}