package com.hana053.micropost.pages.signup

import com.hana053.micropost.interactors.UserInteractor
import com.hana053.micropost.services.LoginService
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class SignupServiceImpl(
    private val userInteractor: UserInteractor,
    private val loginService: LoginService
) : SignupService {

    override fun signup(request: UserInteractor.SignupRequest): Observable<Void> {
        return userInteractor.create(request)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { user -> loginService.login(request.email, request.password) }
    }
}