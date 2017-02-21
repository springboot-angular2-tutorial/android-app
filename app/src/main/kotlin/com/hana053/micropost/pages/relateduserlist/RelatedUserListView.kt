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

    private val listUser = content.list_user

    // Events
    val scrollsToBottom: Observable<RecyclerViewScrollEvent> = listUser.scrollEvents()
        .filter { !listUser.canScrollVertically(1) }

    init {
        listUser.layoutManager = LinearLayoutManager(context())
        listUser.adapter = relatedUserListAdapter
    }

}