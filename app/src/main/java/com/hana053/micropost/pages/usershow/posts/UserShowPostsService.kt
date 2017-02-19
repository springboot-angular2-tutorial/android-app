package com.hana053.micropost.pages.usershow.posts

import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.interactor.UserMicropostInteractor
import com.hana053.micropost.service.HttpErrorHandler
import com.hana053.micropost.shared.posts.PostListAdapter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class UserShowPostsService(
    private val postListAdapter: PostListAdapter,
    private val userMicropostInteractor: UserMicropostInteractor,
    private val httpErrorHandler: HttpErrorHandler
) {

    fun loadPosts(userId: Long): Observable<List<Micropost>> {
        val maxId = postListAdapter.getLastItemId()
        val itemCount = postListAdapter.itemCount

        return userMicropostInteractor.loadPrevPosts(userId, maxId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { postListAdapter.addAll(itemCount, it) }
            .doOnError { httpErrorHandler.handleError(it) }
            .onErrorResumeNext { Observable.empty() }
    }
}