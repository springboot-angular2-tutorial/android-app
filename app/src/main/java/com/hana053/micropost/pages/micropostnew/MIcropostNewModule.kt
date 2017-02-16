package com.hana053.micropost.pages.micropostnew

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.content


fun micropostNewModule() = Kodein.Module {

    bind<MicropostNewPresenter>() with autoScopedSingleton(androidActivityScope) {
        MicropostNewPresenter(instance(), instance(), instance())
    }

    bind<MicropostNewView>() with autoScopedSingleton(androidActivityScope) {
        val content = instance<Activity>().content()
        MicropostNewView(content)
    }

    bind<MicropostNewService>() with autoScopedSingleton(androidActivityScope) {
        MicropostNewService(instance())
    }

    bind<MicropostNewNavigator>() with autoScopedSingleton(androidActivityScope) {
        MicropostNewNavigator(instance())
    }

}


