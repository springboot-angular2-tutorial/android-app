package com.hana053.micropost.repository

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton


fun repositoryModule() = Kodein.Module {

    bind<AuthTokenRepository>() with singleton {
        AuthTokenRepositoryImpl(instance())
    }

}