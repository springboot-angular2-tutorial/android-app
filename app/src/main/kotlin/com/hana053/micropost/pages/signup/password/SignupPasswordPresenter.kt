package com.hana053.micropost.pages.signup.password

import com.hana053.micropost.pages.Presenter
import com.hana053.micropost.pages.signup.SignupService
import com.hana053.micropost.pages.signup.SignupState
import com.hana053.micropost.service.Navigator


class SignupPasswordPresenter(
    override val view: SignupPasswordView,
    private val signupState: SignupState,
    private val signupService: SignupService,
    private val navigator: Navigator
) : Presenter<SignupPasswordView> {

    override fun bind() {
        val passwordChanges = view.passwordChanges.share()
        val nextBtnClicks = view.nextBtnClicks.share()

        passwordChanges
            .bindToLifecycle()
            .map { isFormValid(it) }
            .subscribe { view.nextBtnEnabled.call(it) }

        passwordChanges
            .bindToLifecycle()
            .map { !isFormValid(it) && it.isNotBlank() }
            .subscribe { view.passwordInvalidVisibility.call(it) }

        nextBtnClicks
            .bindToLifecycle()
            .withLatestFrom(passwordChanges, { click, password -> password })
            .map {
                signupState.password = it.toString()
                signupState.toSignupRequest()
            }
            .flatMap {
                signupService.signup(it)
                    .withProgressDialog()
            }
            .subscribe { navigator.navigateToMain() }
    }

    private fun isFormValid(password: CharSequence): Boolean = password.length >= 8

}