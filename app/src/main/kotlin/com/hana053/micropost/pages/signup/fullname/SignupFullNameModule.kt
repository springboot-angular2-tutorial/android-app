package com.hana053.micropost.pages.signup.fullname

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.fragment_signup_full_name.*


fun signupFullNameModule() = Kodein.Module {

    bind<SignupFullNamePresenter>() with autoScopedSingleton(androidActivityScope) {
        SignupFullNamePresenter(instance(), instance())
    }

    bind<SignupFullNameView>() with autoScopedSingleton(androidActivityScope) {
        val content = instance<Activity>().fragment_signup_full_name
        SignupFullNameView(content)
    }

}


