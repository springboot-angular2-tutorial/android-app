package com.hana053.micropost.presentation.relateduserlist;

import android.support.v4.app.FragmentActivity;

import com.hana053.micropost.domain.RelatedUser;
import com.hana053.micropost.domain.UserStats;
import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.components.followbtn.FollowBtnService;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ExplicitHasComponent;
import com.hana053.micropost.presentation.relateduserlist.followinglist.FollowingListComponent;
import com.hana053.micropost.presentation.relateduserlist.followinglist.FollowingListModule;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;
import com.hana053.micropost.testing.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RelatedUserListFragmentTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private final long userId = 1;
    private RelatedUserListFragment fragment;

    @Mock
    private RelatedUserListService relatedUserListService;

    @Mock
    private FollowBtnService followBtnService;

    private final Observable<List<RelatedUser>> dummyUsers = Observable
            .just(new RelatedUser(1, "test", "test@test.com", "", 1))
            .toList();

    @Before
    public void setup() {
        when(relatedUserListService.loadRelatedUsers(userId)).thenReturn(dummyUsers);

        fragment = RelatedUserListFragment.newInstance(userId);
        SupportFragmentTestUtil.startFragment(fragment, TestActivity.class);
    }

    @Test
    public void shouldLoadPrevUsersOnScrolledToBottom() {
        triggerScroll();
        verify(relatedUserListService, times(2)).loadRelatedUsers(userId);
    }

    @Test
    public void shouldLoadUsersWhenActivityCreated() {
        verify(relatedUserListService).loadRelatedUsers(userId);
    }

    @Test
    public void shouldStartAndStopFollowBtnService() {
        verify(followBtnService).startObserving();
        fragment.onStop();
        verify(followBtnService).stopObserving();
    }

    private void triggerScroll() {
        final List<RelatedUser> users = Observable.range(1, 1)
                .map(id -> new RelatedUser(id, "test", "test@test.com", "", false, 1, new UserStats()))
                .toList()
                .toBlocking()
                .single();
        fragment.userListAdapter.addAll(0, users);
        TestUtils.populateItems(fragment.getBinding().userRecyclerView);
    }

    private static class TestActivity extends FragmentActivity implements ExplicitHasComponent<FollowingListComponent> {
        @Override
        public FollowingListComponent getComponent() {
            return BaseApplication.component(this)
                    .activityComponent(new ActivityModule(this))
                    .followingListComponent(new FollowingListModule());
        }

        @Override
        public Class<FollowingListComponent> getComponentType() {
            return FollowingListComponent.class;
        }
    }

}