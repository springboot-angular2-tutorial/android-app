package com.hana053.micropost.pages.login

import com.hana053.micropost.pages.Presenter
import com.hana053.micropost.service.LoginService
import com.hana053.micropost.service.Navigator
import rx.Observable

class LoginPresenter(
    override val view: LoginView,
    private val loginService: LoginService,
    private val navigator: Navigator
) : Presenter<LoginView> {

    override fun bind() {
        val emailChanges = view.emailChanges.share()
        val passwordChanges = view.passwordChanges.share()

        val credentials = Observable.combineLatest(emailChanges, passwordChanges, { email, password ->
            Triple(email, password, email.isNotBlank() && password.isNotBlank())
        })

        credentials
            .bindToLifecycle()
            .map { it.third }
            .subscribe { view.loginEnabled.call(it) }

        view.loginClicks
            .bindToLifecycle()
            .withLatestFrom(credentials, { click, credentials ->
                credentials.first to credentials.second
            })
            .flatMap {
                loginService.login(it.first, it.second)
                    .withProgressDialog()
            }
            .subscribe { navigator.navigateToMain() }
    }

}

