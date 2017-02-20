package com.hana053.micropost.pages.login

import android.view.ViewGroup
import com.hana053.micropost.pages.ViewWrapper
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.widget.textChanges
import kotlinx.android.synthetic.main.activity_login.view.*
import rx.Observable

class LoginView(override val content: ViewGroup) : ViewWrapper {

    // Events
    val emailChanges: Observable<String> = content.emailEditText
        .textChanges()
        .map { it.toString() }
    val passwordChanges: Observable<String> = content.passwordEditText
        .textChanges()
        .map { it.toString() }
    val loginClicks = content.loginBtn.clicks()

    // Props
    val loginEnabled = content.loginBtn.enabled()
}
