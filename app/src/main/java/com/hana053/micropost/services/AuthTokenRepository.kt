package com.hana053.micropost.services


interface AuthTokenRepository {
    fun getAuthToken(): String?
    fun setAuthToken(authToken: String)
    fun clearAuthToken()
}