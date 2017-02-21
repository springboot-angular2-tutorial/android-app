package com.hana053.micropost.testing

import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher


fun atPositionOnView(
    position: Int,
    matcher: Matcher<View>,
    targetId: Int
) = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

    override fun matchesSafely(recyclerView: RecyclerView) =
        recyclerView.findViewHolderForAdapterPosition(position)
            .itemView
            .findViewById(targetId)
            .let { matcher.matches(it) }

    override fun describeTo(description: Description?) {
        description?.appendText("has view id $matcher at position $position")
    }
}

