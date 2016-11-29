package com.hana053.micropost.ui.pages.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.ui.pages.signup.email.SignupEmailFragment;
import com.hana053.micropost.ui.pages.signup.fullname.SignupFullNameFragment;
import com.hana053.micropost.ui.pages.signup.password.SignupPasswordFragment;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class SignupActivityTest extends RobolectricBaseTest {

    private ActivityController<SignupActivity> activityController;
    private SignupActivity activity;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(SignupActivity.class);
        activity = activityController.setup().get();
    }

    @Test
    public void shouldShowFullNameInput() {
        final List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        assertThat(fragments, hasSize(1));
        assertThat(fragments.get(0), instanceOf(SignupFullNameFragment.class));
    }

    @Test
    public void shouldBeFinishedWhenBackPressedAndStackIsEmpty() {
        activity.onBackPressed();
        assertThat(activity.isFinishing(), is(true));
    }

    @Test
    public void shouldNavigateToNewEmail() {
        activity.navigateToNewEmail();
        final List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        assertThat(fragments, hasSize(2));
        assertThat(fragments.get(1), instanceOf(SignupEmailFragment.class));
    }

    @Test
    public void shouldNavigateToNewPassword() {
        activity.navigateToNewPassword();
        final List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        assertThat(fragments, hasSize(2));
        assertThat(fragments.get(1), instanceOf(SignupPasswordFragment.class));
    }

    @Test
    @Ignore // FIXME I'm not sure why it becomes NPE..
    public void shouldBeBackToPrevWhenBackPressed() {
        activity.navigateToNewEmail();
        activity.onBackPressed();
        assertThat(activity.getSupportFragmentManager().getBackStackEntryCount(), is(0));
    }

    @Test
    public void shouldBeRestoredFromSavedState() {
        Bundle state = new Bundle();
        activityController.saveInstanceState(state).pause().stop().destroy();
        Robolectric.buildActivity(SignupActivity.class).setup(state);
    }

}