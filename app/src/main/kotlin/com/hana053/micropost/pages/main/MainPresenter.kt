package com.hana053.micropost.pages.main

import com.hana053.micropost.pages.Presenter
import com.hana053.micropost.service.Navigator
import com.hana053.micropost.shared.posts.PostListAdapter
import rx.subjects.PublishSubject


class MainPresenter(
    override val view: MainView,
    private val mainService: MainService,
    private val postListAdapter: PostListAdapter,
    private val navigator: Navigator
) : Presenter<MainView> {

    val newPostSubmittedSubject: PublishSubject<Void> = PublishSubject.create()

    override fun bind() {
        mainService.loadNextFeed()
            .bindToLifecycle()
            .withProgressDialog()
            .subscribe()

        view.swipeRefreshes
            .bindToLifecycle()
            .flatMap { mainService.loadNextFeed() }
            .subscribe { view.swipeRefreshing.call(false) }

        view.scrollsToBottom
            .bindToLifecycle()
            .flatMap {
                mainService.loadPrevFeed()
                    .withProgressDialog()
            }
            .subscribe()

        view.newMicropostClicks
            .bindToLifecycle()
            .subscribe { navigator.navigateToMicropostNew() }

        postListAdapter.avatarClicksSubject
            .bindToLifecycle()
            .subscribe { navigator.navigateToUserShow(it.id) }

        newPostSubmittedSubject
            .bindToLifecycle()
            .flatMap {
                mainService.loadNextFeed()
                    .withProgressDialog()
            }
            .subscribe()
    }

}