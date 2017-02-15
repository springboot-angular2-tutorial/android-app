package com.hana053.micropost.pages.signup

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.pages.signup.email.signupEmailModule
import com.hana053.micropost.pages.signup.fullname.signupFullNameModule
import com.hana053.micropost.pages.signup.password.signupPasswordModule

fun signupModule() = Kodein.Module {

    bind<SignupState>() with autoScopedSingleton(androidActivityScope) {
        SignupState()
    }

    bind<SignupNavigator>() with autoScopedSingleton(androidActivityScope) {
        SignupNavigator(instance())
    }

    bind<SignupService>() with autoScopedSingleton(androidActivityScope) {
        SignupService(instance(), instance())
    }

    import(signupFullNameModule())
    import(signupEmailModule())
    import(signupPasswordModule())

}

