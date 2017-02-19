package com.hana053.micropost.services

import rx.Observable


interface LoginService {

    fun login(email: String, password: String): Observable<Void>

}