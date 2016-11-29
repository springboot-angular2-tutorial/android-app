package com.hana053.micropost.ui.pages.micropostnew;

import android.app.Activity;

import com.hana053.micropost.services.LoginService;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MicropostNewActivityTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    @SuppressWarnings("FieldCanBeLocal")
    private ActivityController<MicropostNewActivity> activityController;

    private MicropostNewActivity activity;

    @Mock
    private LoginService loginService;

    @Before
    public void setup() {
        when(loginService.ensureAuthenticated()).thenReturn(true);

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
        verify(loginService).ensureAuthenticated();
    }

}