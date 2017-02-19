package com.hana053.micropost.pages.usershow.detail

import com.hana053.micropost.domain.User
import com.hana053.micropost.service.Navigator
import com.hana053.micropost.shared.followbtn.FollowBtnService
import com.hana053.micropost.withProgressDialog
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable


class UserShowDetailPresenter(
    private val detailService: UserShowDetailService,
    private val followBtnService: FollowBtnService,
    private val navigator: Navigator
) {

    fun bind(view: UserShowDetailView, userId: Long) {
        getUser(userId, view)
            .bindToLifecycle(view.content)
            .subscribe()

        view.followClicks
            .bindToLifecycle(view.content)
            .flatMap { followBtnService.handleFollowBtnClicks(it) }
            .flatMap { getUser(userId, view) }
            .subscribe()

        view.followersClicks
            .bindToLifecycle(view.content)
            .subscribe { navigator.navigateToFollowerList(userId) }

        view.followingsClicks
            .bindToLifecycle(view.content)
            .subscribe { navigator.navigateToFollowingList(userId) }
    }

    private fun getUser(userId: Long, view: UserShowDetailView): Observable<User> {
        return detailService.getUser(userId)
            .withProgressDialog(view.content)
            .doOnNext { view.render(it) }
    }

}


