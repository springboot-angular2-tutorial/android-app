package com.hana053.micropost.pages.main

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.shared.posts.PostListAdapter
import kotlinx.android.synthetic.main.activity_main.*

fun mainModule() = Kodein.Module {

    bind<PostListAdapter>() with autoScopedSingleton(androidActivityScope) {
        PostListAdapter()
    }

    bind<MainPresenter>() with autoScopedSingleton(androidActivityScope) {
        MainPresenter(instance(), instance(), instance(), instance())
    }

    bind<MainView>() with autoScopedSingleton(androidActivityScope) {
        val content = instance<Activity>().activity_main
        MainView(content, instance())
    }

    bind<MainService>() with autoScopedSingleton(androidActivityScope) {
        MainService(instance(), instance(), instance())
    }

}

