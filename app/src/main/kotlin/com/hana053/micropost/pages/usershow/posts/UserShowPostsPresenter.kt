package com.hana053.micropost.pages.usershow.posts

import com.hana053.micropost.withProgressDialog
import com.trello.rxlifecycle.kotlin.bindToLifecycle


class UserShowPostsPresenter(
    private val userId: Long,
    private val service: UserShowPostsService
) {

    fun bind(view: UserShowPostsView) {
        service.loadPosts(userId)
            .bindToLifecycle(view.content)
            .withProgressDialog(view.content)
            .subscribe()
    }

}