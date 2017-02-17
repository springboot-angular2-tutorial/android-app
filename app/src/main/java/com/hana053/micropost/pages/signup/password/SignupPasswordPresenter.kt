package com.hana053.micropost.pages.signup.password

import android.content.Context
import android.widget.Toast
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.pages.signup.SignupNavigator
import com.hana053.micropost.pages.signup.SignupService
import com.hana053.micropost.pages.signup.SignupState
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.withProgressDialog
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import retrofit2.adapter.rxjava.HttpException


class SignupPasswordPresenter(
    private val signupState: SignupState,
    private val signupService: SignupService,
    private val navigator: Navigator,
    private val signupNavigator: SignupNavigator,
    private val httpErrorHandler: HttpErrorHandler,
    private val context: Context
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
            .subscribe({
                navigator.navigateToMain()
            }, { e ->
                if (isEmailAlreadyTaken(e)) {
                    Toast.makeText(context, "This email is already taken.", Toast.LENGTH_LONG).show()
                    signupNavigator.navigateToPrev()
                } else {
                    httpErrorHandler.handleError(e)
                }
            })
    }

    private fun isFormValid(password: CharSequence): Boolean {
        return password.length >= 8
    }

    private fun isEmailAlreadyTaken(e: Throwable): Boolean {
        return e is HttpException && e.code() == 400
    }

}