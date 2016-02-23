package com.hana053.micropost.presentation.core.listeners;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BottomedViewSyncScrollerTest {

    private final View view = mock(View.class);

    private BottomedViewSyncScroller scroller;

    @Before
    public void setup() {
        scroller = new BottomedViewSyncScroller(view);
        when(view.getHeight()).thenReturn(10);
    }

    @Test
    public void shouldNotMoveWhenScrolledUp() {
        scroller.onScrolled(null, 0, -1);
        verify(view).setTranslationY(0);
    }

    @Test
    public void shouldMoveWhenScrolledDown() {
        scroller.onScrolled(null, 0, 1);
        verify(view).setTranslationY(1);
    }

    @Test
    public void shouldNotMoveOverBottomWhenScrolledDown() {
        scroller.onScrolled(null, 0, 11);
        verify(view).setTranslationY(10);
    }

    @Test
    public void shouldMoveToOriginalPositionWhenOnUpperHalf() {
        scroller.onScrolled(null, 0, 4);
        scroller.onScrollStateChanged(null, RecyclerView.SCROLL_STATE_IDLE);
        verify(view).setTranslationY(0);
    }

    @Test
    public void shouldMoveToBottomWhenOnLowerHalf() {
        scroller.onScrolled(null, 0, 6);
        scroller.onScrollStateChanged(null, RecyclerView.SCROLL_STATE_IDLE);
        verify(view).setTranslationY(10);
    }

}