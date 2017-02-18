package com.hana053.micropost.pages.relateduserlist.followinglist

import android.content.Context
import com.hana053.micropost.R
import com.hana053.micropost.domain.RelatedUser
import com.hana053.micropost.interactors.RelatedUserListInteractor
import com.hana053.micropost.pages.relateduserlist.RelatedUserListAdapter
import com.hana053.micropost.pages.relateduserlist.RelatedUserListService
import com.hana053.micropost.services.HttpErrorHandler
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class FollowingListService(
    private val interactor: RelatedUserListInteractor,
    private val adapter: RelatedUserListAdapter,
    private val httpErrorHandler: HttpErrorHandler,
    context: Context
) : RelatedUserListService {

    private val context: Context

    init {
        this.context = context.applicationContext
    }

    override fun listUsers(userId: Long): Observable<List<RelatedUser>> {
        val maxId = adapter.getLastItemId()
        val itemCount = adapter.itemCount

        return interactor.listFollowings(userId, maxId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { adapter.addAll(itemCount, it) }
            .doOnError { httpErrorHandler.handleError(it) }
            .onErrorResumeNext { Observable.empty() }
    }

    override fun title(): String {
        return context.getString(R.string.Followings)
    }
}