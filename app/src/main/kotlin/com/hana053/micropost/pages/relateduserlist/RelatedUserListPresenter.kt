package com.hana053.micropost.pages.relateduserlist

import com.hana053.micropost.pages.Presenter
import com.hana053.micropost.service.Navigator
import com.hana053.micropost.shared.followbtn.FollowBtnService


class RelatedUserListPresenter(
    override val view: RelatedUserListView,
    private val userId: Long,
    private val relatedUserListService: RelatedUserListService,
    private val relatedUserListAdapter: RelatedUserListAdapter,
    private val followBtnService: FollowBtnService,
    private val navigator: Navigator
) : Presenter<RelatedUserListView> {

    override fun bind() {

        relatedUserListService.listUsers(userId)
            .bindToLifecycle()
            .withProgressDialog()
            .subscribe()

        view.scrolledToBottom
            .bindToLifecycle()
            .flatMap {
                relatedUserListService.listUsers(userId)
                    .withProgressDialog()
            }
            .subscribe()

        relatedUserListAdapter.followBtnClicksSubject
            .bindToLifecycle()
            .flatMap { followBtnService.handleFollowBtnClicks(it) }
            .subscribe()

        relatedUserListAdapter.avatarClicksSubject
            .bindToLifecycle()
            .subscribe { navigator.navigateToUserShow(it.id) }
    }

}