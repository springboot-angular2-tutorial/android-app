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

        val emailAndPassword = Observable.combineLatest(emailChanges, passwordChanges, ::EmailAndPassword)

        emailAndPassword
            .bindToLifecycle()
            .map { it.isValid() }
            .subscribe { view.loginEnabled.call(it) }

        view.loginClicks
            .bindToLifecycle()
            .withLatestFrom(emailAndPassword, { click, emailAndPassword ->
                emailAndPassword
            })
            .flatMap {
                loginService.login(it.email, it.password)
                    .withProgressDialog()
            }
            .subscribe { navigator.navigateToMain() }
    }

    data class EmailAndPassword(val email: String, val password: String) {
        fun isValid() = email.isNotBlank() && password.isNotBlank()
    }

}

