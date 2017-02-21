package com.hana053.micropost.pages.signup.fullname

import android.view.ViewGroup
import com.hana053.micropost.pages.ViewWrapper
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import com.jakewharton.rxbinding.widget.textChanges
import kotlinx.android.synthetic.main.fragment_signup_full_name.view.*


class SignupFullNameView(override val content: ViewGroup) : ViewWrapper {

    // Events
    val fullNameChanges = content.et_full_name.textChanges()
    val nextBtnClicks = content.btn_next.clicks()

    // Props
    val nextBtnEnabled = content.btn_next.enabled()
    val fullNameInvalidVisibility = content.tv_full_name_invalid.visibility()

}