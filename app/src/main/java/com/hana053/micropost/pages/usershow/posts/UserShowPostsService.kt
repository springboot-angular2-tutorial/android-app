package com.hana053.micropost.pages.usershow.posts

import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.interactors.UserMicropostInteractor
import com.hana053.micropost.shared.posts.PostListAdapter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class UserShowPostsService(
    private val postListAdapter: PostListAdapter,
    private val userMicropostInteractor: UserMicropostInteractor
) {

    fun loadPosts(userId: Long): Observable<List<Micropost>> {
        val maxId = postListAdapter.getLastItemId()
        val itemCount = postListAdapter.itemCount

        return userMicropostInteractor.loadPrevPosts(userId, maxId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext({ posts -> postListAdapter.addAll(itemCount, posts) })
    }
}