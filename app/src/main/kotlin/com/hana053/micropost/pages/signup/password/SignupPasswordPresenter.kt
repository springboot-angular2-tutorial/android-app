package com.hana053.micropost.pages.signup.password

import com.hana053.micropost.pages.signup.SignupService
import com.hana053.micropost.pages.signup.SignupState
import com.hana053.micropost.service.Navigator
import com.hana053.micropost.withProgressDialog
import com.trello.rxlifecycle.kotlin.bindToLifecycle


class SignupPasswordPresenter(
    private val signupState: SignupState,
    private val signupService: SignupService,
    private val navigator: Navigator
) {

    fun bind(view: SignupPasswordView) {
        val passwordChanges = view.passwordChanges.share()
        val nextBtnClicks = view.nextBtnClicks.share()

        passwordChanges
            .bindToLifecycle(view.content)
            .map { isFormValid(it) }
            .subscribe {
                view.nextBtnEnabled.call(it)
            }

        passwordChanges
            .bindToLifecycle(view.content)
            .map { !isFormValid(it) && it.isNotBlank() }
            .subscribe {
                view.passwordInvalidVisibility.call(it)
            }

        nextBtnClicks
            .bindToLifecycle(view.content)
            .withLatestFrom(passwordChanges, { click, password -> password })
            .map {
                signupState.password = it.toString()
                signupState.toSignupRequest()
            }
            .flatMap {
                signupService.signup(it)
                    .withProgressDialog(view.content)
            }
            .subscribe { navigator.navigateToMain() }
    }

    private fun isFormValid(password: CharSequence): Boolean {
        return password.length >= 8
    }

}