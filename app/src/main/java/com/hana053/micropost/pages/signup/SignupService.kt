package com.hana053.micropost.pages.signup

import com.hana053.micropost.interactors.UserInteractor
import rx.Observable


interface SignupService {

    fun signup(request: UserInteractor.SignupRequest): Observable<Void>
}