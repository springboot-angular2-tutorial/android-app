package com.hana053.micropost.presentation.main;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.hana053.micropost.presentation.core.services.LoginService;
import com.hana053.micropost.presentation.micropostnew.MicropostNewActivity;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.shadows.ShadowLoginServiceFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@Config(shadows = ShadowLoginServiceFactory.class)
public class MainActivityTest extends RobolectricBaseTest {

    @SuppressWarnings("FieldCanBeLocal")
    private ActivityController<MainActivity> activityController;

    @Mock
    private MainFragment mainFragment;

    @InjectMocks
    private MainActivity activity;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(MainActivity.class);
        activity = activityController.setup().get();
    }

    @Test
    public void shouldReuseTaggedFragment() {
        Fragment fragment = activity.getSupportFragmentManager().getFragments().get(0);
        assertThat(fragment, notNullValue());
        assertThat(fragment.getTag(), is(MainActivity.TAG_MAIN_FRAGMENT));

        final ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        shadowActivity.recreate();

        Fragment newFragment = activity.getSupportFragmentManager().getFragments().get(0);
        assertThat(newFragment, is(fragment));
    }

    @Test
    public void shouldLoadNextFeedOnActivityResultWith_REQUEST_POST_and_RESULT_OK() {
        MockitoAnnotations.initMocks(this);
        activity.onActivityResult(MainActivity.REQUEST_POST, Activity.RESULT_OK, null);
        verify(mainFragment).loadFeed();
    }

    @Test
    public void shouldNavigateToMicropostNew() {
        activity.navigateToMicropostNew();
        ShadowActivity shadow = Shadows.shadowOf(activity);
        ShadowActivity.IntentForResult intentForResult = shadow.getNextStartedActivityForResult();
        assertThat(intentForResult.intent.getComponent().getClassName(), is(MicropostNewActivity.class.getName()));
    }

    @Test
    public void shouldEnsureAuthenticated() {
        LoginService loginService = getAppComponent().loginService();
        verify(loginService).ensureAuthenticated();
    }

}

