package com.hana053.micropost.pages.relateduserlist

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity.Companion.KEY_LIST_TYPE
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity.Companion.KEY_USER_ID
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity.ListType.FOLLOWER
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity.ListType.FOLLOWING
import com.hana053.micropost.pages.relateduserlist.followerlist.FollowerListService
import com.hana053.micropost.pages.relateduserlist.followinglist.FollowingListService
import com.hana053.micropost.shared.followbtn.followBtnModule
import kotlinx.android.synthetic.main.activity_related_user_list.*


fun relatedUserListModule() = Kodein.Module {

    bind<RelatedUserListAdapter>() with autoScopedSingleton(androidActivityScope) {
        RelatedUserListAdapter()
    }

    bind<RelatedUserListView>() with autoScopedSingleton(androidActivityScope) {
        instance<Activity>().activity_related_user_list
            .let { RelatedUserListView(it, instance()) }
    }

    bind<RelatedUserListPresenter>() with autoScopedSingleton(androidActivityScope) {
        RelatedUserListPresenter(instance(), instance(KEY_USER_ID), instance(), instance(), instance(), instance())
    }

    bind<RelatedUserListService>() with autoScopedSingleton(androidActivityScope) {
        when (instance<RelatedUserListActivity.ListType>()) {
            FOLLOWER -> FollowerListService(instance(), instance(), instance(), instance())
            FOLLOWING -> FollowingListService(instance(), instance(), instance(), instance())
        }
    }

    bind<Long>(KEY_USER_ID) with autoScopedSingleton(androidActivityScope) {
        extras().getLong(KEY_USER_ID)
    }

    bind<RelatedUserListActivity.ListType>() with autoScopedSingleton(androidActivityScope) {
        extras().getSerializable(KEY_LIST_TYPE) as RelatedUserListActivity.ListType
    }

    import(followBtnModule())
}

private fun Kodein.extras() = instance<Activity>().intent.extras


