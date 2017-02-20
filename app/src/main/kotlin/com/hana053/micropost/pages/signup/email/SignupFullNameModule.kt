package com.hana053.micropost.pages.signup.email

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.fragment_signup_email.*


fun signupEmailModule() = Kodein.Module {

    bind<SignupEmailPresenter>() with autoScopedSingleton(androidActivityScope) {
        SignupEmailPresenter(instance(), instance(), instance())
    }

    bind<SignupEmailView>() with autoScopedSingleton(androidActivityScope) {
        instance<Activity>().fragment_signup_email.let(::SignupEmailView)
    }

}


