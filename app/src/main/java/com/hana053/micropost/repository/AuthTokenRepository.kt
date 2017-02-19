package com.hana053.micropost.repository


interface AuthTokenRepository {
    fun get(): String?
    fun set(authToken: String)
    fun clear()
}