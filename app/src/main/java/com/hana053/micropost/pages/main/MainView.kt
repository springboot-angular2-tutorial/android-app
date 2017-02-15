package com.hana053.micropost.pages.main

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.hana053.micropost.shared.posts.PostListAdapter
import com.jakewharton.rxbinding.support.v4.widget.refreshes
import com.jakewharton.rxbinding.support.v4.widget.refreshing
import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding.support.v7.widget.scrollEvents
import com.jakewharton.rxbinding.view.clicks
import kotlinx.android.synthetic.main.activity_main.view.*
import rx.Observable

class MainView(
    val content: ViewGroup,
    postListAdapter: PostListAdapter
) {

    private val postRecyclerView = content.postRecyclerView
    private val swipeRefreshLayout = content.swipeRefreshLayout

    // Events
    val swipeRefreshes = swipeRefreshLayout.refreshes()
    val scrolledToBottom: Observable<RecyclerViewScrollEvent> = postRecyclerView
        .scrollEvents()
        .filter { !postRecyclerView.canScrollVertically(1) }
    val newMicropostClicks = content.newMicropostBtn.clicks()

    // Props
    val swipeRefreshing = swipeRefreshLayout.refreshing()

    init {
        val context = content.context
        postRecyclerView.layoutManager = LinearLayoutManager(context)
        postRecyclerView.adapter = postListAdapter
    }

}