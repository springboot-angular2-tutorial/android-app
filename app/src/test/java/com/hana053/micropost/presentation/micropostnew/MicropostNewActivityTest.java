package com.hana053.micropost.presentation.micropostnew;

import android.app.Activity;

import com.hana053.micropost.presentation.core.services.LoginService;
import com.hana053.micropost.testing.shadows.ShadowLoginServiceFactory;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@Config(shadows = ShadowLoginServiceFactory.class)
public class MicropostNewActivityTest extends RobolectricBaseTest {

    @SuppressWarnings("FieldCanBeLocal")
    private ActivityController<MicropostNewActivity> activityController;
    private MicropostNewActivity activity;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(MicropostNewActivity.class);
        activity = activityController.setup().get();
    }

    @Test
    public void shouldBeClosed() {
        ShadowActivity shadow = Shadows.shadowOf(activity);
        shadow.clickMenuItem(android.R.id.home);
        assertThat(activity.isFinishing(), is(true));
    }

    @Test
    public void shouldBeClosedWhenPosted() {
        activity.finishWithPost();
        ShadowActivity shadow = Shadows.shadowOf(activity);
        assertThat(shadow.getResultCode(), is(Activity.RESULT_OK));
        assertThat(activity.isFinishing(), is(true));
    }

    @Test
    public void shouldEnsureAuthenticated() {
        LoginService loginService = getAppComponent().loginService();
        verify(loginService).ensureAuthenticated();
    }

}