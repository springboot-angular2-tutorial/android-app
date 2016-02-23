package com.hana053.micropost.presentation.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.FrameLayout;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.domain.User;
import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.List;

import rx.Observable;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MainFragmentTest extends RobolectricBaseTest {

    private MainFragment fragment;
    private TestActivity activity;

    private FrameLayout newMicropostBtn;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Before
    public void setup() {
        fragment = new MainFragment();
        SupportFragmentTestUtil.startFragment(fragment, TestActivity.class);
        activity = (TestActivity) fragment.getActivity();

        newMicropostBtn = fragment.getBinding().newMicropostBtn;
        swipeRefreshLayout = fragment.getBinding().swipeRefreshLayout;

        fragment.getBinding().executePendingBindings();
    }

    @Test
    public void shouldMoveToMicropostNewWhenClickedButton() {
        newMicropostBtn.performClick();
        verify(activity.mainCtrl).navigateToMicropostNew();
    }

    @Test
    public void shouldLoadNextFeedOnSwipeRefresh() {
        fragment.mainService = spy(fragment.mainService);
        fragment.onSwipeRefresh().onRefresh();
        verify(fragment.mainService).loadNextFeed();
        assertThat(swipeRefreshLayout.isRefreshing(), is(false));
    }

    @Test
    public void shouldLoadPrevFeedOnScrolledToBottom() {
        fragment.mainService = spy(fragment.mainService);
        triggerScroll();
        verify(fragment.mainService).loadPrevFeed();
    }

    @Test
    public void shouldBeRestoredFromSavedState() {
        final List<Micropost> posts = Observable.range(1, 2)
                .map(id -> new Micropost(id, "", 0, new User(1, "test", "test@test.com")))
                .toList()
                .toBlocking()
                .single();
        fragment.postListAdapter.addAll(0, posts);

        final ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        shadowActivity.recreate();

        final List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        // fragment must be reused.
        assertThat(fragments, hasSize(1));
        assertThat(fragments.get(0), is(fragment));
        assertThat(fragment.postListAdapter.getItemCount(), is(2));
    }

    private void triggerScroll() {
        // need more than two items to scroll
        final List<Micropost> posts = Observable.range(1, 2)
                .map(id -> new Micropost(id, "", 0, new User(1, "test", "test@test.com")))
                .toList()
                .toBlocking()
                .single();
        fragment.postListAdapter.addAll(0, posts);
        TestUtils.populateItems(fragment.getBinding().postRecyclerView);
    }

    private static class TestActivity extends FragmentActivity implements HasComponent<MainComponent> {
        private final MainCtrl mainCtrl = mock(MainCtrl.class);

        @Override
        public MainComponent getComponent() {
            return DaggerMainComponent.builder()
                    .appComponent(BaseApplication.component(this))
                    .activityModule(new ActivityModule(this))
                    .mainModule(new MainModule(mainCtrl))
                    .build();
        }
    }

}

