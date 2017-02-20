package com.hana053.micropost.pages.signup.password

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.fragment_signup_password.*


fun signupPasswordModule() = Kodein.Module {

    bind<SignupPasswordPresenter>() with autoScopedSingleton(androidActivityScope) {
        SignupPasswordPresenter(instance(), instance(), instance(), instance())
    }

    bind<SignupPasswordView>() with autoScopedSingleton(androidActivityScope) {
        val content = instance<Activity>().fragment_signup_password
        SignupPasswordView(content)
    }

}


