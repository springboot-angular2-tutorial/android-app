package com.hana053.micropost.pages.relateduserlist

import com.hana053.micropost.service.Navigator
import com.hana053.micropost.shared.followbtn.FollowBtnService
import com.hana053.micropost.withProgressDialog
import com.trello.rxlifecycle.kotlin.bindToLifecycle


class RelatedUserListPresenter(
    private val userId: Long,
    private val relatedUserListService: RelatedUserListService,
    private val relatedUserListAdapter: RelatedUserListAdapter,
    private val followBtnService: FollowBtnService,
    private val navigator: Navigator
) {

    fun bind(view: RelatedUserListView) {

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