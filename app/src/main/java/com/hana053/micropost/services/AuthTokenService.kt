package com.hana053.micropost.services

import com.hana053.micropost.domain.User


interface AuthTokenService {

    fun getAuthToken(): String?
    fun setAuthToken(authToken: String)
    fun clearAuthToken()
    fun isMyself(user: User): Boolean

}