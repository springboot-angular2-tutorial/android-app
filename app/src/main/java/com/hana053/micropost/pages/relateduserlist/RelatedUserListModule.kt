package com.hana053.micropost.pages.relateduserlist

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.content
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity.ListType.FOLLOWER
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity.ListType.FOLLOWING
import com.hana053.micropost.pages.relateduserlist.followerlist.FollowerListService
import com.hana053.micropost.pages.relateduserlist.followinglist.FollowingListService
import com.hana053.micropost.shared.followbtn.followBtnModule


fun relatedUserListModule() = Kodein.Module {

    bind<RelatedUserListAdapter>() with autoScopedSingleton(androidActivityScope) {
        RelatedUserListAdapter()
    }

    bind<RelatedUserListView>() with autoScopedSingleton(androidActivityScope) {
        val content = instance<Activity>().content()
        RelatedUserListView(content, instance())
    }

    bind<RelatedUserListPresenter>(FOLLOWER) with autoScopedSingleton(androidActivityScope) {
        RelatedUserListPresenter(instance(FOLLOWER), instance(), instance(), instance(), instance(), instance())
    }

    bind<RelatedUserListPresenter>(FOLLOWING) with autoScopedSingleton(androidActivityScope) {
        RelatedUserListPresenter(instance(FOLLOWING), instance(), instance(), instance(), instance(), instance())
    }

    bind<RelatedUserListService>(FOLLOWER) with autoScopedSingleton(androidActivityScope) {
        FollowerListService(instance(), instance(), instance())
    }

    bind<RelatedUserListService>(FOLLOWING) with autoScopedSingleton(androidActivityScope) {
        FollowingListService(instance(), instance(), instance())
    }

    import(followBtnModule())
}


