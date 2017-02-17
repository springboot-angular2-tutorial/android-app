package com.hana053.micropost.services

import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.interactors.LoginInteractor
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class LoginServiceImpl(
    private val loginInteractor: LoginInteractor,
    private val authTokenService: AuthTokenService,
    private val navigator: Navigator
) : LoginService {

    override fun login(email: String, password: String): Observable<Void> {
        return loginInteractor
            .login(LoginInteractor.LoginRequest(email, password))
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { authTokenService.setAuthToken(it.token) }
            .map { null }
    }

    override fun logout() {
        authTokenService.clearAuthToken()
        navigator.navigateToTop()
    }

    override fun auth(): Boolean {
        if (authTokenService.getAuthToken().isNullOrBlank()) {
            logout()
            return false
        }
        return true
    }

}