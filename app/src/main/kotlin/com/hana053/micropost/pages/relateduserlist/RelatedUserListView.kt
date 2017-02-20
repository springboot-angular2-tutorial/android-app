package com.hana053.micropost.pages.relateduserlist

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.hana053.micropost.pages.ViewWrapper
import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding.support.v7.widget.scrollEvents
import kotlinx.android.synthetic.main.activity_related_user_list.view.*
import rx.Observable


class RelatedUserListView(
    override val content: ViewGroup,
    relatedUserListAdapter: RelatedUserListAdapter
) : ViewWrapper {

    private val userRecyclerView = content.userRecyclerView

    // Events
    val scrolledToBottom: Observable<RecyclerViewScrollEvent> = userRecyclerView.scrollEvents()
        .filter { !userRecyclerView.canScrollVertically(1) }

    init {
        val context = content.context
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.adapter = relatedUserListAdapter
    }

}