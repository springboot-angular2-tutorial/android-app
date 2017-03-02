package com.hana053.micropost.pages.signup

import android.content.Context
import android.widget.Toast
import com.hana053.micropost.interactor.UserInteractor
import com.hana053.micropost.service.HttpErrorHandler
import com.hana053.micropost.service.LoginService
import retrofit2.HttpException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class SignupServiceImpl(
    private val userInteractor: UserInteractor,
    private val loginService: LoginService,
    private val signupNavigator: SignupNavigator,
    private val httpErrorHandler: HttpErrorHandler,
    context: Context
) : SignupService {

    private val context = context.applicationContext

    override fun signup(request: UserInteractor.SignupRequest): Observable<Void> =
        userInteractor.create(request)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                when {
                    isEmailAlreadyTaken(it) -> {
                        Toast.makeText(context, "This email is already taken.", Toast.LENGTH_LONG).show()
                        signupNavigator.navigateToPrev()
                    }
                    else -> httpErrorHandler.handleError(it)
                }
            }
            .onErrorResumeNext { Observable.empty() }
            .flatMap {
                loginService.login(request.email, request.password)
            }

    private fun isEmailAlreadyTaken(e: Throwable) = e is HttpException && e.code() == 400

}