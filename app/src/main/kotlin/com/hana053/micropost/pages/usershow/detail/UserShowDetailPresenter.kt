package com.hana053.micropost.pages.usershow.detail

import com.hana053.micropost.domain.User
import com.hana053.micropost.pages.Presenter
import com.hana053.micropost.service.Navigator
import com.hana053.micropost.shared.followbtn.FollowBtnService
import rx.Observable


class UserShowDetailPresenter(
    override val view: UserShowDetailView,
    private val userId: Long,
    private val detailService: UserShowDetailService,
    private val followBtnService: FollowBtnService,
    private val navigator: Navigator
) : Presenter<UserShowDetailView> {

    override fun bind() {
        getUser()
            .bindToLifecycle()
            .subscribe()

        view.followClicks
            .bindToLifecycle()
            .flatMap { followBtnService.handleFollowBtnClicks(it) }
            .flatMap { getUser() }
            .subscribe()

        view.followersClicks
            .bindToLifecycle()
            .subscribe { navigator.navigateToFollowerList(userId) }

        view.followingsClicks
            .bindToLifecycle()
            .subscribe { navigator.navigateToFollowingList(userId) }
    }

    private fun getUser(): Observable<User> {
        return detailService.getUser(userId)
            .withProgressDialog()
            .doOnNext { view.render(it) }
    }

}


