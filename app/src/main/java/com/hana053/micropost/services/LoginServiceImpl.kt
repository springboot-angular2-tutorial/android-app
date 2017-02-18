package com.hana053.micropost.services

import android.content.Context
import android.widget.Toast
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.interactors.LoginInteractor
import retrofit2.adapter.rxjava.HttpException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class LoginServiceImpl(
    private val loginInteractor: LoginInteractor,
    private val authTokenService: AuthTokenService,
    private val navigator: Navigator,
    private val httpErrorHandler: HttpErrorHandler,
    context: Context
) : LoginService {

    private val context: Context = context.applicationContext

    override fun login(email: String, password: String): Observable<Void> {
        return loginInteractor
            .login(LoginInteractor.LoginRequest(email, password))
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { authTokenService.setAuthToken(it.token) }
            .doOnError { err ->
                if (err is HttpException && err.code() == 401) {
                    Toast.makeText(context, "Email or Password is wrong.", Toast.LENGTH_LONG).show()
                } else {
                    httpErrorHandler.handleError(err)
                }
            }
            .onErrorResumeNext(Observable.empty())
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