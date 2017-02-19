package com.hana053.micropost.services

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton


fun serviceModule() = Kodein.Module {

    bind<HttpErrorHandler>() with singleton {
        HttpErrorHandlerImpl(instance(), instance())
    }

    bind<AuthTokenRepository>() with singleton {
        AuthTokenRepositoryImpl(instance())
    }

    bind<AuthService>() with singleton {
        AuthServiceImpl(instance(), instance())
    }

    bind<LoginService>() with singleton {
        LoginServiceImpl(instance(), instance(), instance(), instance())
    }

}