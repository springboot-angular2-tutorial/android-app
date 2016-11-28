package com.hana053.micropost.presentation.relateduserlist.followerlist;

import android.content.Intent;

import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class FollowerListActivityTest extends RobolectricBaseTest {

    @SuppressWarnings("FieldCanBeLocal")
    private ActivityController<FollowerListActivity> activityController;
    private FollowerListActivity activity;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(FollowerListActivity.class);
        Intent intent = new Intent();
        intent.putExtra(FollowerListActivity.KEY_USER_ID, 1L);
        activity = activityController.withIntent(intent).setup().get();
    }

    @Test
    public void shouldBeCreated() {
        assertThat(activity, instanceOf(FollowerListActivity.class));
    }

}