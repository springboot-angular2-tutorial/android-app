package com.hana053.micropost.pages.usershow

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.pages.usershow.UserShowActivity.Companion.KEY_USER_ID
import com.hana053.micropost.pages.usershow.detail.userShowDetailModule
import com.hana053.micropost.pages.usershow.posts.userShowPostsModule
import com.hana053.micropost.shared.followbtn.followBtnModule

fun userShowModule() = Kodein.Module {

    bind<Long>(KEY_USER_ID) with autoScopedSingleton(androidActivityScope) {
        instance<Activity>().intent.extras.getLong(KEY_USER_ID)
    }

    import(userShowDetailModule())
    import(userShowPostsModule())
    import(followBtnModule())

}


