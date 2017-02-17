package com.hana053.micropost.services

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton


fun serviceModule() = Kodein.Module {

    bind<HttpErrorHandler>() with singleton {
        HttpErrorHandlerImpl(instance(), instance())
    }

    bind<AuthTokenService>() with singleton {
        AuthTokenServiceImpl(instance())
    }

    bind<LoginService>() with singleton {
        LoginServiceImpl(instance(), instance(), instance())
    }

}