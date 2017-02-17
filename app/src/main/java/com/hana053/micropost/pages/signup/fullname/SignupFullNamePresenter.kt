package com.hana053.micropost.pages.signup.fullname

import com.hana053.micropost.pages.signup.SignupNavigator
import com.hana053.micropost.pages.signup.SignupState
import com.trello.rxlifecycle.kotlin.bindToLifecycle


class SignupFullNamePresenter(
    private val signupState: SignupState,
    private val signupNavigator: SignupNavigator
) {

    fun bind(view: SignupFullNameView) {
        val fullNameChanges = view.fullNameChanges.share()

        fullNameChanges
            .bindToLifecycle(view.content)
            .map { isFormValid(it) }
            .subscribe {
                view.nextBtnEnabled.call(it)
            }

        fullNameChanges
            .bindToLifecycle(view.content)
            .map { !isFormValid(it) && it.isNotBlank() }
            .subscribe {
                view.fullNameInvalidVisibility.call(it)
            }

        view.nextBtnClicks
            .bindToLifecycle(view.content)
            .withLatestFrom(fullNameChanges, { click, fullName -> fullName })
            .subscribe {
                signupState.fullName = it.toString()
                signupNavigator.navigateToEmail()
            }
    }

    private fun isFormValid(fullName: CharSequence): Boolean {
        return fullName.length >= 4
    }

}