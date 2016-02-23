package com.hana053.micropost.presentation.core.listeners;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BottomedViewSyncScroller extends RecyclerView.OnScrollListener {

    private final View view;
    private int delta;

    public BottomedViewSyncScroller(View view) {
        this.view = view;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        delta += dy;
        if (delta < 0) delta = 0;
        if (delta > view.getHeight()) delta = view.getHeight();
        view.setTranslationY(delta);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (delta < view.getHeight() / 2) delta = 0;
        else delta = view.getHeight();
        view.setTranslationY(delta);
    }
}

