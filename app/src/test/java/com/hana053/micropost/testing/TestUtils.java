package com.hana053.micropost.testing;

import android.view.ViewGroup;

import org.robolectric.shadows.ShadowListView;

public class TestUtils {

    /**
     * I want to use ShadowListView#populateItems() for RecyclerView.
     *
     * @param viewGroup
     * @see ShadowListView#populateItems()
     */
    public static void populateItems(ViewGroup viewGroup) {
        viewGroup.measure(0, 0);
        viewGroup.layout(0, 0, 100, 10000);
    }
}
