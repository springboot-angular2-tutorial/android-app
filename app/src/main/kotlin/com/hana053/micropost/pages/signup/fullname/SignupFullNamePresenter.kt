package com.hana053.micropost.pages.signup.fullname

import com.hana053.micropost.pages.Presenter
import com.hana053.micropost.pages.signup.SignupNavigator
import com.hana053.micropost.pages.signup.SignupState


class SignupFullNamePresenter(
    override val view: SignupFullNameView,
    private val signupState: SignupState,
    private val signupNavigator: SignupNavigator
) : Presenter<SignupFullNameView> {

    override fun bind() {
        val fullNameChanges = view.fullNameChanges.share()

        fullNameChanges
            .bindToLifecycle()
            .map { isFormValid(it) }
            .subscribe { view.nextBtnEnabled.call(it) }

        fullNameChanges
            .bindToLifecycle()
            .map { !isFormValid(it) && it.isNotBlank() }
            .subscribe { view.fullNameInvalidVisibility.call(it) }

        view.nextBtnClicks
            .bindToLifecycle()
            .withLatestFrom(fullNameChanges, { click, fullName -> fullName })
            .subscribe {
                signupState.fullName = it.toString()
                signupNavigator.navigateToEmail()
            }
    }

    private fun isFormValid(fullName: CharSequence) = fullName.length >= 4

}