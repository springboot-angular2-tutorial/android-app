package com.hana053.micropost.pages.login

import android.content.Context
import android.widget.Toast
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.services.LoginService
import com.hana053.micropost.withProgressDialog
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import retrofit2.adapter.rxjava.HttpException
import rx.Observable

class LoginPresenter(
    private val loginService: LoginService,
    private val navigator: Navigator,
    private val httpErrorHandler: HttpErrorHandler,
    context: Context
) {

    private val context: Context

    init {
        // Confirm getting application context
        this.context = context.applicationContext
    }

    fun bind(view: LoginView) {
        val emailChanges = view.emailChanges.share()
        val passwordChanges = view.passwordChanges.share()

        val credentials = Observable.combineLatest(emailChanges, passwordChanges, { email, password ->
            Triple(email, password, email.isNotBlank() && password.isNotBlank())
        })

        credentials
            .bindToLifecycle(view.content)
            .map { it.third }
            .subscribe { view.loginEnabled.call(it) }

        view.loginClicks
            .bindToLifecycle(view.content)
            .withLatestFrom(credentials, { click, credentials ->
                credentials.first to credentials.second
            })
            .flatMap {
                loginService.login(it.first, it.second)
                    .withProgressDialog(view.content)
            }
            .subscribe({
                navigator.navigateToMain()
            }, { e ->
                if (e is HttpException && e.code() == 401) {
                    Toast.makeText(context, "Email or Password is wrong.", Toast.LENGTH_LONG).show()
                } else {
                    httpErrorHandler.handleError(e)
                }
            })
    }

}

