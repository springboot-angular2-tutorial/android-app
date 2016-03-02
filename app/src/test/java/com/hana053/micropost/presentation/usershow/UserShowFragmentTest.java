package com.hana053.micropost.presentation.usershow;

import android.view.View;

import com.hana053.micropost.databinding.UserShowBinding;
import com.hana053.micropost.domain.User;
import com.hana053.micropost.domain.UserStats;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.shadows.ShadowNavigatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(Enclosed.class)
public class UserShowFragmentTest {

    public static abstract class Base extends RobolectricBaseTest {
        protected UserShowFragment fragment;

        protected void setupFragment(User u) {
            fragment = UserShowFragment.newInstance(u);
            SupportFragmentTestUtil.startFragment(fragment, UserShowTestActivity.class);
            fragment.getBinding().executePendingBindings();
        }
    }

    public static class Common extends Base {
        private final UserStats userStats = new UserStats(0, 1, 2, false);
        private final User user = new User(1, "test user", "test@test.com", false, userStats);

        @Before
        public void setup() {
            setupFragment(user);
        }

        @Test
        @Config(shadows = ShadowNavigatorFactory.class)
        public void shouldNavigateToFollowingsWhenClickedFollowings() {
            fragment.getBinding().followings.performClick();
            verify(fragment.navigator).navigateToFollowings(user.getId());
        }

        @Test
        @Config(shadows = ShadowNavigatorFactory.class)
        public void shouldNavigateToFollowersWhenClickedFollowers() {
            fragment.getBinding().followers.performClick();
            verify(fragment.navigator).navigateToFollowers(user.getId());
        }

        @Test
        public void shouldShowUser() {
            final UserShowBinding binding = fragment.getBinding();
            assertThat(binding.userName.getText(), is(user.getName()));
            assertThat(binding.followingCnt.getText(), is(userStats.followingCnt + " "));
            assertThat(binding.followerCnt.getText(), is(userStats.followerCnt + " "));
        }
    }

    public static class WhenUserIsNotFollowed extends Base {
        private final UserStats userStats = new UserStats(0, 1, 2, false);

        @Before
        public void setup() {
            setupFragment(new User(1, "test user", "test@test.com", false, userStats));
        }

        @Test
        public void shouldShowFollowBtn() {
            assertThat(fragment.getBinding().followBtn.getText(), is("Follow"));
        }
    }

    public static class WhenUserIsFollowed extends Base {
        private final UserStats userStats = new UserStats(0, 1, 2, true);

        @Before
        public void setup() {
            setupFragment(new User(1, "test user", "test@test.com", false, userStats));
        }

        @Test
        public void shouldShowUnfollowBtn() {
            assertThat(fragment.getBinding().followBtn.getText(), is("Unfollow"));
        }
    }

    public static class WhenUserIsMyself extends Base {
        private final UserStats userStats = new UserStats(0, 1, 2, false);

        @Before
        public void setup() {
            setupFragment(new User(1, "test user", "test@test.com", true, userStats));
        }

        @Test
        public void shouldNotShowFollowOrUnfollowBtn() {
            assertThat(fragment.getBinding().followBtn.getVisibility(), is(View.GONE));
        }
    }

}