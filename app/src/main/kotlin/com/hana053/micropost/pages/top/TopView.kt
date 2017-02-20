package com.hana053.micropost.pages.top

import android.view.ViewGroup
import com.hana053.micropost.pages.ViewWrapper
import com.jakewharton.rxbinding.view.clicks
import kotlinx.android.synthetic.main.activity_top.view.*

class TopView(override val content: ViewGroup) : ViewWrapper {

    val signupClicks = content.signupBtn.clicks()
    val loginClicks = content.loginBtn.clicks()
}