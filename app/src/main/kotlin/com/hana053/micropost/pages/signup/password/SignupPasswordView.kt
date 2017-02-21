package com.hana053.micropost.pages.signup.password

import android.view.ViewGroup
import com.hana053.micropost.pages.ViewWrapper
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import com.jakewharton.rxbinding.widget.textChanges
import kotlinx.android.synthetic.main.fragment_signup_password.view.*


class SignupPasswordView(override val content: ViewGroup) : ViewWrapper {

    // Events
    val passwordChanges = content.et_password.textChanges()
    val nextBtnClicks = content.btn_next.clicks()

    // Props
    val nextBtnEnabled = content.btn_next.enabled()
    val passwordInvalidVisibility = content.tv_password_invalid.visibility()
}