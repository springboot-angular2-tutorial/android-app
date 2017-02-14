package com.hana053.micropost.pages.login

import android.view.ViewGroup
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.widget.textChanges
import kotlinx.android.synthetic.main.activity_login.view.*

class LoginView(val content: ViewGroup) {

    // Events
    val emailChanges = content.emailEditText.textChanges().map { it.toString() }!!
    val passwordChanges = content.passwordEditText.textChanges().map { it.toString() }!!
    val loginClicks = content.loginBtn.clicks()

    // Props
    val loginEnabled = content.loginBtn.enabled()
}
