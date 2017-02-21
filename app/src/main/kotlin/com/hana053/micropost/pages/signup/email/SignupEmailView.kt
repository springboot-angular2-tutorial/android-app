package com.hana053.micropost.pages.signup.email

import android.view.ViewGroup
import com.hana053.micropost.pages.ViewWrapper
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import com.jakewharton.rxbinding.widget.textChanges
import kotlinx.android.synthetic.main.fragment_signup_email.view.*


class SignupEmailView(override val content: ViewGroup) : ViewWrapper {

    // Events
    val emailChanges = content.et_email.textChanges()
    val nextBtnClicks = content.btn_next.clicks()

    // Props
    val nextBtnEnabled = content.btn_next.enabled()
    val emailInvalidVisibility = content.tv_email_invalid.visibility()

}