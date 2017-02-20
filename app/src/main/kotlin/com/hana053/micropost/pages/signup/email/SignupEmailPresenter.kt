package com.hana053.micropost.pages.signup.email

import com.hana053.micropost.pages.signup.SignupNavigator
import com.hana053.micropost.pages.signup.SignupState
import com.trello.rxlifecycle.kotlin.bindToLifecycle


class SignupEmailPresenter(
    private val signupState: SignupState,
    private val signupNavigator: SignupNavigator
) {

    fun bind(view: SignupEmailView) {
        val emailChanges = view.emailChanges.share()

        emailChanges
            .bindToLifecycle(view.content)
            .map { isFormValid(it) }
            .subscribe {
                view.nextBtnEnabled.call(it)
            }

        emailChanges
            .bindToLifecycle(view.content)
            .map { !isFormValid(it) && it.isNotBlank() }
            .subscribe {
                view.emailInvalidVisibility.call(it)
            }

        view.nextBtnClicks
            .bindToLifecycle(view.content)
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