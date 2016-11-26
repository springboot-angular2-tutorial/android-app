package com.hana053.micropost.presentation.login;

import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

@SuppressWarnings("FieldCanBeLocal")
public class LoginActivityTest extends RobolectricBaseTest {

    private ActivityController<LoginActivity> activityController;
    private LoginActivity activity;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(LoginActivity.class);
        activity = activityController.create().get();
    }

    @Test
    public void shouldBeCreated() {
        assertThat(activity, instanceOf(LoginActivity.class));
    }

}