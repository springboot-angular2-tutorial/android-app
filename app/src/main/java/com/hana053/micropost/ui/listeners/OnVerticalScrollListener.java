package com.hana053.micropost.ui.listeners;

import android.support.v7.widget.RecyclerView;

public abstract class OnVerticalScrollListener
        extends RecyclerView.OnScrollListener {

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!recyclerView.canScrollVertically(-1)) {
            onScrolledToTop();
        }
        if (!recyclerView.canScrollVertically(1)) {
            onScrolledToBottom();
        }
        if (dy < 0) {
            onScrolledUp(dy);
        }
        if (dy > 0) {
            onScrolledDown(dy);
        }
    }

    public void onScrolledUp(int dy) {
    }

    public void onScrolledDown(int dy) {
    }

    public void onScrolledToTop() {
    }

    public void onScrolledToBottom() {
    }
}

