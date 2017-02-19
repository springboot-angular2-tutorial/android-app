package com.hana053.micropost.service

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance

fun serviceModule() = Kodein.Module {

    bind<Navigator>() with autoScopedSingleton(androidActivityScope) {
        NavigatorImpl(instance())
    }

    bind<HttpErrorHandler>() with autoScopedSingleton(androidActivityScope) {
        HttpErrorHandlerImpl(instance(), instance())
    }

    bind<AuthService>() with autoScopedSingleton(androidActivityScope) {
        AuthServiceImpl(instance(), instance())
    }

    bind<LoginService>() with autoScopedSingleton(androidActivityScope) {
        LoginServiceImpl(instance(), instance(), instance(), instance())
    }

}

