package com.hana053.micropost.pages.signup.password

import android.content.Context
import android.widget.Toast
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.pages.signup.SignupNavigator
import com.hana053.micropost.pages.signup.SignupService
import com.hana053.micropost.pages.signup.SignupState
import com.hana053.micropost.plusAssign
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.withProgressDialog
import retrofit2.adapter.rxjava.HttpException
import rx.subscriptions.CompositeSubscription


class SignupPasswordPresenter(
    private val signupState: SignupState,
    private val signupService: SignupService,
    private val navigator: Navigator,
    private val signupNavigator: SignupNavigator,
    private val httpErrorHandler: HttpErrorHandler,
    private val context: Context
) {

    fun bind(view: SignupPasswordView): CompositeSubscription {
        val subscriptions = CompositeSubscription()

        val passwordChanges = view.passwordChanges.share()
        val nextBtnClicks = view.nextBtnClicks.share()

        subscriptions += passwordChanges
            .map { isFormValid(it) }
            .subscribe {
                view.nextBtnEnabled.call(it)
            }

        subscriptions += passwordChanges
            .map { !isFormValid(it) && it.isNotBlank() }
            .subscribe {
                view.passwordInvalidVisibility.call(it)
            }

        subscriptions += nextBtnClicks
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

        return subscriptions
    }

    private fun isFormValid(password: CharSequence): Boolean {
        return password.length >= 8
    }

    private fun isEmailAlreadyTaken(e: Throwable): Boolean {
        return e is HttpException && e.code() == 400
    }

}