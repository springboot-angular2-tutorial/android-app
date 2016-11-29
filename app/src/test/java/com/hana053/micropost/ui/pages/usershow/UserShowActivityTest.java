package com.hana053.micropost.ui.pages.usershow;

import android.content.Intent;

import com.hana053.micropost.domain.User;
import com.hana053.micropost.services.LoginService;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.parceler.Parcels;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class UserShowActivityTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private UserShowActivity activity;

    @Mock
    private LoginService loginService;

    @Before
    public void setup() {
        ActivityController<UserShowActivity> activityController = Robolectric.buildActivity(UserShowActivity.class);
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
        verify(loginService).ensureAuthenticated();
    }

}