package com.hana053.micropost.ui.pages.top;

import com.hana053.micropost.services.HttpErrorHandler;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TopActivityTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    @SuppressWarnings("FieldCanBeLocal")
    private ActivityController<TopActivity> activityController;
    private TopActivity activity;

    @Mock
    HttpErrorHandler httpErrorHandler;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(TopActivity.class);
        activity = activityController.setup().get();
    }

    @Test
    public void shouldBeCreated() {
        assertThat(activity.httpErrorHandler, is(httpErrorHandler));
        assertThat(activity, instanceOf(TopActivity.class));
    }

}