package com.hana053.micropost.pages.signup

import android.support.v4.app.FragmentActivity
import com.hana053.micropost.R
import com.hana053.micropost.pages.signup.email.SignupEmailFragment
import com.hana053.micropost.pages.signup.password.SignupPasswordFragment


class SignupNavigatorImpl(
    private val activity: FragmentActivity
) : SignupNavigator {

    override fun navigateToEmail() {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SignupEmailFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToPassword() {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SignupPasswordFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToPrev() {
        val backStackCnt = activity.supportFragmentManager.backStackEntryCount
        if (backStackCnt == 0) {
            activity.finish()
            return
        }
        activity.supportFragmentManager.popBackStack()
    }

}