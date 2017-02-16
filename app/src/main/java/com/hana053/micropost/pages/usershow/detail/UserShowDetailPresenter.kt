package com.hana053.micropost.pages.usershow.detail

import com.hana053.micropost.domain.User
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.plusAssign
import com.hana053.micropost.shared.followbtn.FollowBtnService
import com.hana053.micropost.withProgressDialog
import rx.Observable
import rx.subscriptions.CompositeSubscription


class UserShowDetailPresenter(
    private val detailService: UserShowDetailService,
    private val followBtnService: FollowBtnService,
    private val navigator: Navigator,
    private val httpErrorHandler: HttpErrorHandler
) {

    fun bind(view: UserShowDetailView, userId: Long): CompositeSubscription {
        val subscriptions = CompositeSubscription()

        subscriptions += getUser(userId, view)
            .subscribe({}, { httpErrorHandler.handleError(it) })

        subscriptions += view.followClicks
            .flatMap { followBtnService.handleFollowBtnClicks(it) }
            .flatMap { getUser(userId, view) }
            .subscribe({}, { httpErrorHandler.handleError(it) })

        subscriptions += view.followersClicks
            .subscribe { navigator.navigateToFollowerList(userId) }

        subscriptions += view.followingsClicks
            .subscribe { navigator.navigateToFollowingList(userId) }

        return subscriptions
    }

    private fun getUser(userId: Long, view: UserShowDetailView): Observable<User> {
        return detailService.getUser(userId)
            .withProgressDialog(view.content)
            .doOnNext { view.render(it) }
    }

}


