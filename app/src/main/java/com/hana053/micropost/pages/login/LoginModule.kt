package com.hana053.micropost.pages.login

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance

fun loginModule() = Kodein.Module {

    bind<LoginPresenter>() with autoScopedSingleton(androidActivityScope) {
        LoginPresenter(instance(), instance(), instance(), instance())
    }

}



