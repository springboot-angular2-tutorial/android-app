package com.hana053.micropost.pages.signup

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.pages.signup.email.signupEmailModule
import com.hana053.micropost.pages.signup.fullname.signupFullNameModule

fun signupModule() = Kodein.Module {

    bind<SignupState>() with autoScopedSingleton(androidActivityScope) {
        SignupState()
    }

    bind<SignupNavigator>() with autoScopedSingleton(androidActivityScope) {
        SignupNavigator(instance())
    }

    import(signupFullNameModule())
    import(signupEmailModule())

}

