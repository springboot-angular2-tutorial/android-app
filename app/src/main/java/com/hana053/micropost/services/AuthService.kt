package com.hana053.micropost.services

import com.hana053.micropost.domain.User


interface AuthService {
    fun isMyself(user: User): Boolean
    fun logout()
    fun auth(): Boolean
}