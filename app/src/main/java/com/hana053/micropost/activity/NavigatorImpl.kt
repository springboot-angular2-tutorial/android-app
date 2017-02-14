package com.hana053.micropost.activity

import android.app.Activity
import android.content.Intent
import com.hana053.micropost.pages.login.LoginActivity

class NavigatorImpl(private val activity: Activity) : Navigator {

    override fun navigateToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
    }
//
//    override fun navigateToSignup() {
//        val intent = Intent(activity, SignupActivity::class.java)
//        activity.startActivity(intent)
//    }
}
