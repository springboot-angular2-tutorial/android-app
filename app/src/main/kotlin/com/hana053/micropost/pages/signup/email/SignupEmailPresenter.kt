package com.hana053.micropost.pages.signup.email

import com.hana053.micropost.pages.Presenter
import com.hana053.micropost.pages.signup.SignupNavigator
import com.hana053.micropost.pages.signup.SignupState


class SignupEmailPresenter(
    override val view: SignupEmailView,
    private val signupState: SignupState,
    private val signupNavigator: SignupNavigator
) : Presenter<SignupEmailView> {

    override fun bind() {
        val emailChanges = view.emailChanges.share()

        emailChanges
            .bindToLifecycle()
            .map { isFormValid(it) }
            .subscribe { view.nextBtnEnabled.call(it) }

        emailChanges
            .bindToLifecycle()
            .map { !isFormValid(it) && it.isNotBlank() }
            .subscribe { view.emailInvalidVisibility.call(it) }

        view.nextBtnClicks
            .bindToLifecycle()
            .withLatestFrom(emailChanges, { click, email -> email })
            .subscribe {
                signupState.email = it.toString()
                signupNavigator.navigateToPassword()
            }
    }

    private fun isFormValid(email: CharSequence): Boolean {
        val emailPattern = "^[^0-9][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@\\[?([\\d\\w\\.-]+)]?$"
        return email.matches(emailPattern.toRegex())
    }

}