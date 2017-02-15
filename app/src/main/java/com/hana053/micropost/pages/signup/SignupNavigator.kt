package com.hana053.micropost.pages.signup

import android.support.v4.app.FragmentActivity
import com.hana053.micropost.R
import com.hana053.micropost.pages.signup.email.SignupEmailFragment


class SignupNavigator(
    private val activity: FragmentActivity
) {

    fun navigateToEmail() {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SignupEmailFragment())
            .addToBackStack(null)
            .commit()
    }

    fun navigateToPassword() {
//        activity.supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.container, SignupPasswordFragment())
//            .addToBackStack(null)
//            .commit()
    }

    fun navigateToPrev() {
        val backStackCnt = activity.supportFragmentManager.backStackEntryCount
        if (backStackCnt == 0) {
            activity.finish()
            return
        }
        activity.supportFragmentManager.popBackStack()
    }

}