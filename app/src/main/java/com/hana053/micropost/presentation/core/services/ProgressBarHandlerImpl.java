package com.hana053.micropost.presentation.core.services;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class ProgressBarHandlerImpl implements ProgressBarHandler {

    private final ProgressBar progressBar;

    public ProgressBarHandlerImpl(Activity activity) {
        ViewGroup layout = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();

        progressBar = new ProgressBar(activity, null, android.R.attr.progressBarStyle);
        progressBar.setIndeterminate(true);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rl = new RelativeLayout(activity);

        rl.setGravity(Gravity.CENTER);
        rl.addView(progressBar);

        layout.addView(rl, params);

        hide();
    }

    @Override
    public void show() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        progressBar.setVisibility(View.GONE);
    }
}
