package com.hana053.micropost.presentation.core.services;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProgressBarHandlerTest extends RobolectricBaseTest {

    private ProgressBarHandler progressBarHandler;
    private ProgressBar progressBar;

    @Before
    public void setup() {
        Activity activity = Robolectric.setupActivity(Activity.class);
        progressBarHandler = new ProgressBarHandler(activity);

        final ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();
        final RelativeLayout relativeLayout = (RelativeLayout) rootView.getChildAt(1);
        progressBar = (ProgressBar) relativeLayout.getChildAt(0);
    }

    @Test
    public void shouldShowOrHideProgressBar() {
        assertThat(progressBar.getVisibility(), is(View.GONE));
        progressBarHandler.show();
        assertThat(progressBar.getVisibility(), is(View.VISIBLE));
        progressBarHandler.hide();
        assertThat(progressBar.getVisibility(), is(View.GONE));
    }

}