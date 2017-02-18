package com.hana053.micropost.pages.signup.password

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance


fun signupPasswordModule() = Kodein.Module {

    bind<SignupPasswordPresenter>() with autoScopedSingleton(androidActivityScope) {
        SignupPasswordPresenter(instance(), instance(), instance())
    }

}


