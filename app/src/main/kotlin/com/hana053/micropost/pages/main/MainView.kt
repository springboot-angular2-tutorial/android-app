package com.hana053.micropost.pages.main

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.hana053.micropost.pages.ViewWrapper
import com.hana053.micropost.shared.posts.PostListAdapter
import com.jakewharton.rxbinding.support.v4.widget.refreshes
import com.jakewharton.rxbinding.support.v4.widget.refreshing
import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding.support.v7.widget.scrollEvents
import com.jakewharton.rxbinding.view.clicks
import kotlinx.android.synthetic.main.activity_main.view.*
import rx.Observable

class MainView(
    override val content: ViewGroup,
    postListAdapter: PostListAdapter
) : ViewWrapper {

    private val listPost = content.list_post
    private val swipeRefresh = content.swipe_refresh

    // Events
    val swipeRefreshes = swipeRefresh.refreshes()
    val scrollsToBottom: Observable<RecyclerViewScrollEvent> = listPost.scrollEvents()
        .filter { !listPost.canScrollVertically(1) }
    val newMicropostClicks = content.btn_new_micropost.clicks()

    // Props
    val swipeRefreshing = swipeRefresh.refreshing()

    init {
        listPost.layoutManager = LinearLayoutManager(context())
        listPost.adapter = postListAdapter
    }

}