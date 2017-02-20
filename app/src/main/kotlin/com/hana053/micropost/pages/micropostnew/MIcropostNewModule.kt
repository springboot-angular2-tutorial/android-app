package com.hana053.micropost.pages.micropostnew

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_micropost_new.*


fun micropostNewModule() = Kodein.Module {

    bind<MicropostNewPresenter>() with autoScopedSingleton(androidActivityScope) {
        MicropostNewPresenter(instance(), instance())
    }

    bind<MicropostNewView>() with autoScopedSingleton(androidActivityScope) {
        val content = instance<Activity>().activity_micropost_new
        MicropostNewView(content)
    }

    bind<MicropostNewService>() with autoScopedSingleton(androidActivityScope) {
        MicropostNewService(instance(), instance())
    }

    bind<MicropostNewNavigator>() with autoScopedSingleton(androidActivityScope) {
        MicropostNewNavigator(instance())
    }

}


