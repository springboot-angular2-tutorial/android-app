package com.hana053.micropost.pages.usershow.detail

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.content
import kotlinx.android.synthetic.main.user_detail.view.*

fun userShowDetailModule() = Kodein.Module {

    bind<UserShowDetailService>() with autoScopedSingleton(androidActivityScope) {
        UserShowDetailService(instance(), instance())
    }

    bind<UserShowDetailView>() with autoScopedSingleton(androidActivityScope) {
        val content = instance<Activity>().content().userDetail
        UserShowDetailView(content)
    }

    bind<UserShowDetailPresenter>() with autoScopedSingleton(androidActivityScope) {
        UserShowDetailPresenter(instance(), instance(), instance())
    }

}

