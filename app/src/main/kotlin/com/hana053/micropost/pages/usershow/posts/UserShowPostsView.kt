package com.hana053.micropost.pages.usershow.posts

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.hana053.micropost.pages.ViewWrapper
import com.hana053.micropost.shared.posts.PostListAdapter
import kotlinx.android.synthetic.main._user_posts.view.*


class UserShowPostsView(
    override val content: ViewGroup,
    postListAdapter: PostListAdapter
) : ViewWrapper {

    private val listPost = content.list_post

    init {
        with(listPost) {
            layoutManager = LinearLayoutManager(context())
            adapter = postListAdapter
            isNestedScrollingEnabled = false
        }
    }
}