package com.hana053.micropost.pages.usershow.posts

import com.hana053.micropost.pages.Presenter


class UserShowPostsPresenter(
    override val view: UserShowPostsView,
    private val userId: Long,
    private val service: UserShowPostsService
) : Presenter<UserShowPostsView> {

    override fun bind() {
        service.loadPosts(userId)
            .bindToLifecycle()
            .withProgressDialog()
            .subscribe()
    }

}