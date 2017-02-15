package com.hana053.micropost.pages.main

import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.shared.posts.PostListAdapter
import com.hana053.myapp.interactors.FeedInteractor
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainService(
    private val feedInteractor: FeedInteractor,
    private val postListAdapter: PostListAdapter
) {

    fun loadNextFeed(): Observable<List<Micropost>> {
        val sinceId = postListAdapter.getFirstItemId()
        return feedInteractor.loadNextFeed(sinceId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { postListAdapter.addAll(0, it) }
    }

    fun loadPrevFeed(): Observable<List<Micropost>> {
        val maxId = postListAdapter.getLastItemId()
        val itemCount = postListAdapter.itemCount
        return feedInteractor.loadPrevFeed(maxId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { postListAdapter.addAll(itemCount, it) }
    }

}