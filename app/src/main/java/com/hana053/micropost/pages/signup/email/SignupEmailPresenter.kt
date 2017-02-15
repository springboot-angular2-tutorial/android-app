package com.hana053.micropost.pages.signup.email

import com.hana053.micropost.pages.signup.SignupNavigator
import com.hana053.micropost.pages.signup.SignupState
import com.hana053.micropost.plusAssign
import rx.subscriptions.CompositeSubscription


class SignupEmailPresenter(
    private val signupState: SignupState,
    private val signupNavigator: SignupNavigator
) {

    fun bind(view: SignupEmailView): CompositeSubscription {
        val subscriptions = CompositeSubscription()

        val emailChanges = view.emailChanges.share()

        subscriptions += emailChanges
            .map { isFormValid(it) }
            .subscribe {
                view.nextBtnEnabled.call(it)
            }

        subscriptions += emailChanges
            .map { !isFormValid(it) && it.isNotBlank() }
            .subscribe {
                view.emailInvalidVisibility.call(it)
            }

        subscriptions += view.nextBtnClicks
            .withLatestFrom(emailChanges, { click, email -> email })
            .subscribe {
                signupState.email = it.toString()
                signupNavigator.navigateToPassword()
            }

        return subscriptions
    }

    private fun isFormValid(email: CharSequence): Boolean {
        val emailPattern = "^[^0-9][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@\\[?([\\d\\w\\.-]+)]?$"
        return email.matches(emailPattern.toRegex())
    }

}