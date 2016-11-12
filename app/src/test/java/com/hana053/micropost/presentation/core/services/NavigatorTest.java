package com.hana053.micropost.presentation.core.services;

import android.app.Activity;
import android.content.Intent;

import com.hana053.micropost.domain.User;
import com.hana053.micropost.presentation.login.LoginActivity;
import com.hana053.micropost.presentation.main.MainActivity;
import com.hana053.micropost.presentation.relateduserlist.followerlist.FollowerListActivity;
import com.hana053.micropost.presentation.relateduserlist.followinglist.FollowingListActivity;
import com.hana053.micropost.presentation.signup.SignupActivity;
import com.hana053.micropost.presentation.usershow.UserShowActivity;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.parceler.Parcels;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NavigatorTest extends RobolectricBaseTest {

    private Activity activity;
    private Navigator navigator;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(Activity.class);
        navigator = new Navigator(activity);
    }

    @Test
    public void shouldNavigateToUserShow() {
        final User user = new User(1, "test", "test@test.com", "");
        navigator.navigateToUserShow(user);
        final ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        final Intent intent = shadowActivity.getNextStartedActivity();
        assertThat(intent.getComponent().getClassName(), is(UserShowActivity.class.getName()));
        assertThat(Parcels.unwrap(intent.getExtras().getParcelable(UserShowActivity.KEY_USER)), equalTo(user));
    }

    @Test
    public void shouldNavigateToMain() {
        navigator.navigateToMain();
        final ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        final Intent intent = shadowActivity.getNextStartedActivity();
        assertThat(intent.getComponent().getClassName(), is(MainActivity.class.getName()));
    }

    @Test
    public void shouldNavigateToSignup() {
        navigator.navigateToSignup();
        final ShadowActivity shadow = Shadows.shadowOf(activity);
        final Intent intent = shadow.getNextStartedActivity();
        assertThat(intent.getComponent().getClassName(), is(SignupActivity.class.getName()));
    }

    @Test
    public void shouldNavigateToLogin() {
        navigator.navigateToLogin();
        final ShadowActivity shadow = Shadows.shadowOf(activity);
        final Intent intent = shadow.getNextStartedActivity();
        assertThat(intent.getComponent().getClassName(), is(LoginActivity.class.getName()));
    }

    @Test
    public void shouldNavigateToFollowings() {
        navigator.navigateToFollowings(1);
        final ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        final Intent intent = shadowActivity.getNextStartedActivity();
        assertThat(intent.getComponent().getClassName(), is(FollowingListActivity.class.getName()));
        assertThat(intent.getExtras().getLong(FollowingListActivity.KEY_USER_ID), is(1L));
    }

    @Test
    public void shouldNavigateToFollowers() {
        navigator.navigateToFollowers(1);
        final ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        final Intent intent = shadowActivity.getNextStartedActivity();
        assertThat(intent.getComponent().getClassName(), is(FollowerListActivity.class.getName()));
        assertThat(intent.getExtras().getLong(FollowingListActivity.KEY_USER_ID), is(1L));
    }

}