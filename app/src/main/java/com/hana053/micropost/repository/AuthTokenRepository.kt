package com.hana053.micropost.repository


interface AuthTokenRepository {
    fun getAuthToken(): String?
    fun setAuthToken(authToken: String)
    fun clearAuthToken()
}