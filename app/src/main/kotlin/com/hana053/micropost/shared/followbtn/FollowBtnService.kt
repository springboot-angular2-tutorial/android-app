package com.hana053.micropost.shared.followbtn

import com.hana053.micropost.domain.User
import com.hana053.micropost.interactor.RelationshipInteractor
import com.hana053.micropost.service.HttpErrorHandler
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers


class FollowBtnService(
    private val relationshipInteractor: RelationshipInteractor,
    private val httpErrorHandler: HttpErrorHandler
) {
    fun handleFollowBtnClicks(view: FollowBtnView): Observable<User> {
        val obs = if (view.isFollowState()) follow(view) else unfollow(view)
        return obs.withBtnDisabled(view.enabled)
            .doOnError { httpErrorHandler.handleError(it) }
            .onErrorResumeNext { Observable.empty() }
            .map { view.user }
    }

    private fun follow(view: FollowBtnView) =
        relationshipInteractor.follow(view.user.id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { view.toUnfollow() }

    private fun unfollow(view: FollowBtnView) =
        relationshipInteractor.unfollow(view.user.id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { view.toFollow() }

    private fun <T> Observable<T>.withBtnDisabled(enabled: Action1<in Boolean>) =
        Observable.using({
            enabled.call(false)
        }, { this }, {
            enabled.call(true)
        })
}