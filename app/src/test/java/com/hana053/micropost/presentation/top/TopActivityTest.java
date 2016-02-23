package com.hana053.micropost.presentation.top;

import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class TopActivityTest extends RobolectricBaseTest {

    @SuppressWarnings("FieldCanBeLocal")
    private ActivityController<TopActivity> activityController;
    private TopActivity activity;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(TopActivity.class);
        activity = activityController.setup().get();
    }

    @Test
    public void shouldBeCreated() {
        assertThat(activity, instanceOf(TopActivity.class));
    }

}