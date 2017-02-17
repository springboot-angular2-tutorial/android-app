package com.hana053.micropost.pages.main

import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.plusAssign
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.shared.posts.PostListAdapter
import com.hana053.micropost.withProgressDialog
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription


class MainPresenter(
    private val mainService: MainService,
    private val postListAdapter: PostListAdapter,
    private val navigator: Navigator,
    private val httpErrorHandler: HttpErrorHandler
) {

    val newPostSubmittedSubject: PublishSubject<Void> = PublishSubject.create()

    fun bind(view: MainView): CompositeSubscription {
        val subscriptions = CompositeSubscription()

        subscriptions += mainService.loadNextFeed()
            .withProgressDialog(view.content)
            .subscribe({}, { httpErrorHandler.handleError(it) })

        subscriptions += view.swipeRefreshes
            .flatMap { mainService.loadNextFeed() }
            .subscribe({
                view.swipeRefreshing.call(false)
            }, {
                view.swipeRefreshing.call(false)
                httpErrorHandler.handleError(it)
            })

        subscriptions += view.scrolledToBottom
            .flatMap {
                mainService.loadPrevFeed()
                    .withProgressDialog(view.content)
            }
            .subscribe({}, { httpErrorHandler.handleError(it) })

        subscriptions += view.newMicropostClicks
            .subscribe { navigator.navigateToMicropostNew() }

        subscriptions += postListAdapter.avatarClicksSubject
            .subscribe { navigator.navigateToUserShow(it.id) }

        subscriptions += newPostSubmittedSubject
            .flatMap {
                mainService.loadNextFeed()
                    .withProgressDialog(view.content)
            }
            .subscribe({}, { httpErrorHandler.handleError(it) })

        return subscriptions
    }

}