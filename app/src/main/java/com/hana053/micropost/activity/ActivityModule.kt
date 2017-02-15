package com.hana053.micropost.activity

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance

fun activityModule() = Kodein.Module {

    bind<Navigator>() with autoScopedSingleton(androidActivityScope) {
        NavigatorImpl(instance())
    }

}


