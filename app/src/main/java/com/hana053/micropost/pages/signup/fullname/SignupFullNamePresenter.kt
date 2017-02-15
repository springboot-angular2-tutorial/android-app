package com.hana053.micropost.pages.signup.fullname

import com.hana053.micropost.pages.signup.SignupNavigator
import com.hana053.micropost.pages.signup.SignupState
import com.hana053.micropost.plusAssign
import rx.subscriptions.CompositeSubscription


class SignupFullNamePresenter(
    private val signupState: SignupState,
    private val signupNavigator: SignupNavigator
) {

    fun bind(view: SignupFullNameView): CompositeSubscription {
        val subscriptions = CompositeSubscription()

        val fullNameChanges = view.fullNameChanges.share()

        subscriptions += fullNameChanges
            .map { isFormValid(it) }
            .subscribe {
                view.nextBtnEnabled.call(it)
            }

        subscriptions += fullNameChanges
            .map { !isFormValid(it) && it.isNotBlank() }
            .subscribe {
                view.fullNameInvalidVisibility.call(it)
            }

        subscriptions += view.nextBtnClicks
            .withLatestFrom(fullNameChanges, { click, fullName -> fullName })
            .subscribe {
                signupState.fullName = it.toString()
                signupNavigator.navigateToEmail()
            }

        return subscriptions
    }

    private fun isFormValid(fullName: CharSequence): Boolean {
        return fullName.length >= 4
    }

}