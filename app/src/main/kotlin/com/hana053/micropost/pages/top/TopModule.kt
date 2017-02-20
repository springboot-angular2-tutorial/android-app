package com.hana053.micropost.pages.top

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.content

fun topModule() = Kodein.Module {

    bind<TopPresenter>() with autoScopedSingleton(androidActivityScope) {
        TopPresenter(instance())
    }

    bind<TopView>() with autoScopedSingleton(androidActivityScope) {
        val content = instance<Activity>().content()
        TopView(content)
    }

}
