package com.hana053.micropost.pages.top

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance

fun topModule() = Kodein.Module {
    bind<TopPresenter>() with autoScopedSingleton(androidActivityScope) {
        TopPresenter(instance())
    }
}
