package com.hana053.micropost.service

import com.hana053.micropost.domain.User
import com.hana053.micropost.repository.AuthTokenRepository
import com.nimbusds.jose.JWSObject
import java.lang.Long
import java.text.ParseException


class AuthServiceImpl(
    private val authTokenRepository: AuthTokenRepository,
    private val navigator: Navigator
) : AuthService {

    override fun isMyself(user: User): Boolean {
        val authToken = authTokenRepository.get() ?: return false
        try {
            val jwsObject = JWSObject.parse(authToken)
            val sub = jwsObject.payload.toJSONObject()["sub"].toString()
            return user.id == Long.valueOf(sub)
        } catch (e: ParseException) {
            throw RuntimeException(e)
        }

    }

    override fun logout() {
        authTokenRepository.clear()
        navigator.navigateToTop()
    }

    override fun auth(): Boolean {
        if (authTokenRepository.get().isNullOrBlank()) {
            logout()
            return false
        }
        return true
    }

}