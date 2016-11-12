package com.hana053.micropost.presentation.usershow;

import android.content.Intent;

import com.hana053.micropost.domain.User;
import com.hana053.micropost.presentation.core.services.LoginService;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.shadows.ShadowLoginServiceFactory;

import org.junit.Before;
import org.junit.Test;
import org.parceler.Parcels;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@Config(shadows = ShadowLoginServiceFactory.class)
public class UserShowActivityTest extends RobolectricBaseTest {

    @SuppressWarnings("FieldCanBeLocal")
    private ActivityController<UserShowActivity> activityController;
    private UserShowActivity activity;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(UserShowActivity.class);
        final Intent intent = new Intent();
        final User user = new User(1, "", "", "");
        intent.putExtra(UserShowActivity.KEY_USER, Parcels.wrap(user));
        activity = activityController.withIntent(intent).create().get();
    }

    @Test
    public void shouldBeCreated() {
        assertThat(activity, instanceOf(UserShowActivity.class));
    }

    @Test
    public void shouldEnsureAuthenticated() {
        LoginService loginService = getAppComponent().loginService();
        verify(loginService).ensureAuthenticated();
    }

}