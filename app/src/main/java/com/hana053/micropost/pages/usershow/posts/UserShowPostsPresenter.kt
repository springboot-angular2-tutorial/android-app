package com.hana053.micropost.pages.usershow.posts

import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.withProgressDialog
import rx.subscriptions.CompositeSubscription


class UserShowPostsPresenter(
    private val service: UserShowPostsService,
    private val httpErrorHandler: HttpErrorHandler
) {

    fun bind(view: UserShowPostsView, userId: Long): CompositeSubscription {
        val subscriptions = CompositeSubscription()

        service.loadPosts(userId)
            .withProgressDialog(view.content)
            .subscribe({}, { httpErrorHandler.handleError(it) })
        return subscriptions
    }

}