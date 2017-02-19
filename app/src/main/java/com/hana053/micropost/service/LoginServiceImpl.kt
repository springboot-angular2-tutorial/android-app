package com.hana053.micropost.service

import android.content.Context
import android.widget.Toast
import com.hana053.micropost.interactor.LoginInteractor
import com.hana053.micropost.repository.AuthTokenRepository
import retrofit2.adapter.rxjava.HttpException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class LoginServiceImpl(
    private val loginInteractor: LoginInteractor,
    private val authTokenRepository: AuthTokenRepository,
    private val httpErrorHandler: HttpErrorHandler,
    context: Context
) : LoginService {

    private val context: Context = context.applicationContext

    override fun login(email: String, password: String): Observable<Void> {
        return loginInteractor
            .login(LoginInteractor.LoginRequest(email, password))
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { authTokenRepository.set(it.token) }
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

}