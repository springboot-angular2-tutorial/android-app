package com.hana053.micropost.presentation.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.FrameLayout;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.domain.User;
import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;
import com.hana053.micropost.testing.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.List;

import rx.Observable;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainFragmentTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private MainFragment fragment;
    private TestActivity activity;

    private FrameLayout newMicropostBtn;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Mock
    MainCtrl mainCtrl;

    @Mock
    MainService mainService;

    @Before
    public void setup() {
        when(mainService.loadPrevFeed()).thenReturn(Observable.empty());
        when(mainService.loadNextFeed()).thenReturn(Observable.empty());

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
        verify(mainCtrl).navigateToMicropostNew();
    }

    @Test
    public void shouldLoadNextFeedOnSwipeRefresh() {
        fragment.onSwipeRefresh().onRefresh();
        verify(mainService, times(2)).loadNextFeed();
        assertThat(swipeRefreshLayout.isRefreshing(), is(false));
    }

    @Test
    public void shouldLoadPrevFeedOnScrolledToBottom() {
        triggerScroll();
        verify(mainService).loadPrevFeed();
    }

    @Test
    public void shouldBeRestoredFromSavedState() {
        final List<Micropost> posts = Stream.of(1, 2)
                .map(id -> new Micropost(id, "", 0, new User(1, "test", "test@test.com", "")))
                .collect(Collectors.toList());
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
        final List<Micropost> posts = Stream.of(1, 2)
                .map(id -> new Micropost(id, "", 0, new User(1, "test", "test@test.com", "")))
                .collect(Collectors.toList());
        fragment.postListAdapter.addAll(0, posts);
        TestUtils.populateItems(fragment.getBinding().postRecyclerView);
    }

    private static class TestActivity extends FragmentActivity implements MainCtrl, HasComponent<MainComponent> {
        @Override
        public MainComponent getComponent() {
            return BaseApplication.component(this)
                    .activityComponent(new ActivityModule(this))
                    .mainComponent(new MainModule(this));
        }

        @Override
        public void navigateToMicropostNew() {
        }
    }

}

