package com.hana053.micropost.pages.signup.fullname

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance


fun signupFullNameModule() = Kodein.Module {

    bind<SignupFullNamePresenter>() with autoScopedSingleton(androidActivityScope) {
        SignupFullNamePresenter(instance(), instance())
    }

}


