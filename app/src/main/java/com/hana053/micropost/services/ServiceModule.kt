package com.hana053.micropost.services

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidActivityScope


fun serviceModule() = Kodein.Module {

    bind<HttpErrorHandler>() with autoScopedSingleton(androidActivityScope)  {
        HttpErrorHandlerImpl(instance(), instance())
    }

    bind<AuthTokenRepository>() with singleton {
        AuthTokenRepositoryImpl(instance())
    }

    bind<AuthService>() with autoScopedSingleton(androidActivityScope) {
        AuthServiceImpl(instance(), instance())
    }

    bind<LoginService>() with autoScopedSingleton(androidActivityScope)  {
        LoginServiceImpl(instance(), instance(), instance(), instance())
    }

}