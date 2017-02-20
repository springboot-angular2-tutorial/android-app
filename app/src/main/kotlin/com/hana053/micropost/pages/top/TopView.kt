package com.hana053.micropost.pages.top

import android.view.View
import com.jakewharton.rxbinding.view.clicks
import kotlinx.android.synthetic.main.activity_top.view.*

class TopView(val content: View) {

    val signupClicks = content.signupBtn.clicks()
    val loginClicks = content.loginBtn.clicks()
}