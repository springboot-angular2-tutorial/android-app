package com.hana053.micropost.presentation.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

public interface MainViewListener {

    View.OnClickListener onClickNewMicropostBtn();

    SwipeRefreshLayout.OnRefreshListener onSwipeRefresh();
}
