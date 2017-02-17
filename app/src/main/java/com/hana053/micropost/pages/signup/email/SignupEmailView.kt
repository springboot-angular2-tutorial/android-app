package com.hana053.micropost.pages.signup.email

import android.view.ViewGroup
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import com.jakewharton.rxbinding.widget.textChanges
import kotlinx.android.synthetic.main.signup_email.view.*


class SignupEmailView(val content: ViewGroup) {

    // Events
    val emailChanges = content.email.textChanges()
    val nextBtnClicks = content.emailNextBtn.clicks()

    // Props
    val nextBtnEnabled = content.emailNextBtn.enabled()
    val emailInvalidVisibility = content.emailInvalid.visibility()

}