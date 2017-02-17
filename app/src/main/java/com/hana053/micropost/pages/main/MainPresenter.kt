package com.hana053.micropost.pages.main

import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.shared.posts.PostListAdapter
import com.hana053.micropost.withProgressDialog
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.subjects.PublishSubject


class MainPresenter(
    private val mainService: MainService,
    private val postListAdapter: PostListAdapter,
    private val navigator: Navigator,
    private val httpErrorHandler: HttpErrorHandler
) {

    val newPostSubmittedSubject: PublishSubject<Void> = PublishSubject.create()

    fun bind(view: MainView) {

        mainService.loadNextFeed()
            .bindToLifecycle(view.content)
            .withProgressDialog(view.content)
            .subscribe({}, { httpErrorHandler.handleError(it) })

        view.swipeRefreshes
            .bindToLifecycle(view.content)
            .flatMap { mainService.loadNextFeed() }
            .subscribe({
                view.swipeRefreshing.call(false)
            }, {
                view.swipeRefreshing.call(false)
                httpErrorHandler.handleError(it)
            })

        view.scrolledToBottom
            .bindToLifecycle(view.content)
            .flatMap {
                mainService.loadPrevFeed()
                    .withProgressDialog(view.content)
            }
            .subscribe({}, { httpErrorHandler.handleError(it) })

        view.newMicropostClicks
            .bindToLifecycle(view.content)
            .subscribe { navigator.navigateToMicropostNew() }

        postListAdapter.avatarClicksSubject
            .bindToLifecycle(view.content)
            .subscribe { navigator.navigateToUserShow(it.id) }

        newPostSubmittedSubject
            .bindToLifecycle(view.content)
            .flatMap {
                mainService.loadNextFeed()
                    .withProgressDialog(view.content)
            }
            .subscribe({}, { httpErrorHandler.handleError(it) })
    }

}