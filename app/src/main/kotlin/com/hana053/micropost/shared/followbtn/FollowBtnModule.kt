package com.hana053.micropost.shared.followbtn

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance

fun followBtnModule() = Kodein.Module {

    bind<FollowBtnService>() with autoScopedSingleton(androidActivityScope) {
        FollowBtnService(instance(), instance())
    }

}


