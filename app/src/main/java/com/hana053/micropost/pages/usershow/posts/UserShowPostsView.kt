package com.hana053.micropost.pages.usershow.posts

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.hana053.micropost.shared.posts.PostListAdapter
import kotlinx.android.synthetic.main.activity_main.view.*


class UserShowPostsView(
    val content: ViewGroup,
    postListAdapter: PostListAdapter
) {

    private val postRecyclerView = content.postRecyclerView

    init {
        val context = content.context
        postRecyclerView.layoutManager = LinearLayoutManager(context)
        postRecyclerView.adapter = postListAdapter
        postRecyclerView.isNestedScrollingEnabled = false
    }
}