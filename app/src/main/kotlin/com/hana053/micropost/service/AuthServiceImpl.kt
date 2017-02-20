package com.hana053.micropost.service

import com.hana053.micropost.domain.User
import com.hana053.micropost.repository.AuthTokenRepository
import com.nimbusds.jose.JWSObject


class AuthServiceImpl(
    private val authTokenRepository: AuthTokenRepository,
    private val navigator: Navigator
) : AuthService {

    override fun isMyself(user: User): Boolean {
        val authToken = authTokenRepository.get() ?: return false
        return user.id == JWSObject.parse(authToken)
            .payload
            .toJSONObject()["sub"]
            .toString()
            .toLong()

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