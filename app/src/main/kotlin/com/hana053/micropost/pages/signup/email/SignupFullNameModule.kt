package com.hana053.micropost.pages.signup.email

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance


fun signupEmailModule() = Kodein.Module {

    bind<SignupEmailPresenter>() with autoScopedSingleton(androidActivityScope) {
        SignupEmailPresenter(instance(), instance())
    }

}


