package com.hana053.micropost.pages.usershow.detail

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance

fun userShowDetailModule() = Kodein.Module {

    bind<UserShowDetailService>() with autoScopedSingleton(androidActivityScope) {
        UserShowDetailService(instance())
    }

    bind<UserShowDetailPresenter>() with autoScopedSingleton(androidActivityScope) {
        UserShowDetailPresenter(instance(), instance(), instance(), instance())
    }

}

