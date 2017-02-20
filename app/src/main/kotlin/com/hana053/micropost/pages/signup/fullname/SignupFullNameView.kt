package com.hana053.micropost.pages.signup.fullname

import android.view.ViewGroup
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import com.jakewharton.rxbinding.widget.textChanges
import kotlinx.android.synthetic.main.fragment_signup_full_name.view.*


class SignupFullNameView(val content: ViewGroup) {

    // Events
    val fullNameChanges = content.fullName.textChanges()
    val nextBtnClicks = content.fullNameNextBtn.clicks()

    // Props
    val nextBtnEnabled = content.fullNameNextBtn.enabled()
    val fullNameInvalidVisibility = content.fullNameInvalid.visibility()

}