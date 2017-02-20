package com.hana053.micropost.pages.login

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_login.*

fun loginModule() = Kodein.Module {

    bind<LoginPresenter>() with autoScopedSingleton(androidActivityScope) {
        LoginPresenter(instance(), instance(), instance())
    }

    bind<LoginView>() with autoScopedSingleton(androidActivityScope) {
        val content = instance<Activity>().activity_login
        LoginView(content)
    }

}
