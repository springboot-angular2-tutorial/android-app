package com.hana053.micropost.pages.relateduserlist

import android.support.v7.app.AppCompatActivity
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.shared.followbtn.FollowBtnService
import com.hana053.micropost.withProgressDialog
import com.trello.rxlifecycle.kotlin.bindToLifecycle


class RelatedUserListPresenter(
    private val relatedUserListService: RelatedUserListService,
    private val activity: AppCompatActivity,
    private val relatedUserListAdapter: RelatedUserListAdapter,
    private val followBtnService: FollowBtnService,
    private val navigator: Navigator
) {

    fun bind(view: RelatedUserListView, userId: Long) {
        activity.title = relatedUserListService.title()
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        relatedUserListService.listUsers(userId)
            .bindToLifecycle(view.content)
            .withProgressDialog(view.content)
            .subscribe()

        view.scrolledToBottom
            .bindToLifecycle(view.content)
            .flatMap {
                relatedUserListService.listUsers(userId)
                    .withProgressDialog(view.content)
            }
            .subscribe()

        relatedUserListAdapter.followBtnClicksSubject
            .bindToLifecycle(view.content)
            .flatMap { followBtnService.handleFollowBtnClicks(it) }
            .subscribe()

        relatedUserListAdapter.avatarClicksSubject
            .bindToLifecycle(view.content)
            .subscribe { navigator.navigateToUserShow(it.id) }
    }

}