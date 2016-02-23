package com.hana053.micropost.presentation.relateduserlist;

import android.content.Intent;
import android.os.Bundle;

import com.hana053.micropost.domain.User;
import com.hana053.micropost.presentation.core.components.avatar.AvatarClickEvent;
import com.hana053.micropost.presentation.core.components.avatar.AvatarViewModel;
import com.hana053.micropost.presentation.core.services.LoginService;
import com.hana053.micropost.presentation.core.services.Navigator;
import com.hana053.micropost.presentation.relateduserlist.followinglist.FollowingListActivity;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RelatedUserListBaseActivityTest extends RobolectricBaseTest {

    private ActivityController<TestActivity> activityController;
    private TestActivity activity;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(TestActivity.class);
        final Intent intent = new Intent();
        intent.putExtra(FollowingListActivity.KEY_USER_ID, 1L);
        activity = activityController.withIntent(intent).setup(new Bundle()).get();
    }

    @Test
    public void shouldEnsureAuthenticated() {
        verify(activity.loginService).ensureAuthenticated();
    }

    @Test
    public void shouldSubscribeEvent() {
        final EventBus eventBus = EventBus.getDefault();
        assertThat(eventBus.isRegistered(activity), is(true));
        activityController.stop();
        assertThat(eventBus.isRegistered(activity), is(false));
    }

    @Test
    public void shouldNavigateToUserShowWhenReceivedEvent() {
        final User user = new User(1, "", "");
        final AvatarViewModel viewModel = new AvatarViewModel(user);
        final AvatarClickEvent event = new AvatarClickEvent(viewModel);
        activity.onEvent(event);
        verify(activity.navigator).navigateToUserShow(user);
    }

    private static class TestActivity extends RelatedUserListBaseActivity {
        @Override
        protected void prepareComponent() {
            loginService = mock(LoginService.class);
            when(loginService.ensureAuthenticated()).thenReturn(true);
            navigator = mock(Navigator.class);
        }
    }

}