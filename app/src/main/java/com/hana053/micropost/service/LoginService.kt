package com.hana053.micropost.service

import rx.Observable


interface LoginService {

    fun login(email: String, password: String): Observable<Void>

}