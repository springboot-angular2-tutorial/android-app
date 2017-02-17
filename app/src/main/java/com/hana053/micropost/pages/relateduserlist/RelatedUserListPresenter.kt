package com.hana053.micropost.pages.relateduserlist

import android.support.v7.app.AppCompatActivity
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.plusAssign
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.shared.followbtn.FollowBtnService
import com.hana053.micropost.withProgressDialog
import rx.subscriptions.CompositeSubscription


class RelatedUserListPresenter(
    private val relatedUserListService: RelatedUserListService,
    private val activity: AppCompatActivity,
    private val relatedUserListAdapter: RelatedUserListAdapter,
    private val followBtnService: FollowBtnService,
    private val navigator: Navigator,
    private val httpErrorHandler: HttpErrorHandler
) {

    fun bind(view: RelatedUserListView, userId: Long): CompositeSubscription {
        val subscriptions = CompositeSubscription()

        activity.title = relatedUserListService.title()
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        subscriptions += relatedUserListService.listUsers(userId)
            .withProgressDialog(view.content)
            .subscribe({}, { httpErrorHandler.handleError(it) })

        subscriptions += relatedUserListAdapter.followBtnClicksSubject
            .flatMap { followBtnService.handleFollowBtnClicks(it) }
            .subscribe({}, { httpErrorHandler.handleError(it) })

        subscriptions += relatedUserListAdapter.avatarClicksSubject
            .subscribe { navigator.navigateToUserShow(it.id) }

        return subscriptions
    }

}