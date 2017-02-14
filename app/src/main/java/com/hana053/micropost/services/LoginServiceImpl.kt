package com.hana053.micropost.services

import android.content.Context
import android.content.Intent
import com.hana053.micropost.interactors.LoginInteractor
import com.hana053.micropost.pages.top.TopActivity
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class LoginServiceImpl(
    private val loginInteractor: LoginInteractor,
    private val authTokenService: AuthTokenService,
    context: Context
) : LoginService {

    private val context: Context

    init {
        // Confirm getting application context
        this.context = context.applicationContext
    }


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
        val intent = Intent(context, TopActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    override fun auth(): Boolean {
        if (authTokenService.getAuthToken().isNullOrBlank()) {
            logout()
            return false
        }
        return true
    }

}