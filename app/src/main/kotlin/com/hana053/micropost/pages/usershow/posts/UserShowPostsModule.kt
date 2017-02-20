package com.hana053.micropost.pages.usershow.posts

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.content
import com.hana053.micropost.shared.posts.PostListAdapter
import kotlinx.android.synthetic.main.user_posts.view.*

fun userShowPostsModule() = Kodein.Module {

    bind<PostListAdapter>() with autoScopedSingleton(androidActivityScope) {
        PostListAdapter()
    }

    bind<UserShowPostsService>() with autoScopedSingleton(androidActivityScope) {
        UserShowPostsService(instance(), instance(), instance())
    }

    bind<UserShowPostsView>() with autoScopedSingleton(androidActivityScope) {
        val content = instance<Activity>().content().userPosts
        UserShowPostsView(content, instance())
    }

    bind<UserShowPostsPresenter>() with autoScopedSingleton(androidActivityScope) {
        UserShowPostsPresenter(instance())
    }

}


