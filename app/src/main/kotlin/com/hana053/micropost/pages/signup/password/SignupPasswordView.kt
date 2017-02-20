package com.hana053.micropost.pages.signup.password

import android.view.ViewGroup
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import com.jakewharton.rxbinding.widget.textChanges
import kotlinx.android.synthetic.main.signup_password.view.*


class SignupPasswordView(val content: ViewGroup) {

    // Events
    val passwordChanges = content.password.textChanges()
    val nextBtnClicks = content.passwordNextBtn.clicks()

    // Props
    val nextBtnEnabled = content.passwordNextBtn.enabled()
    val passwordInvalidVisibility = content.passwordInvalid.visibility()
}