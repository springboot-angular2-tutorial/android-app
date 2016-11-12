package com.hana053.micropost.presentation.relateduserlist;

import android.support.v4.app.FragmentActivity;

import com.hana053.micropost.domain.RelatedUser;
import com.hana053.micropost.domain.UserStats;
import com.hana053.micropost.interactors.RelatedUserListInteractor;
import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ExplicitHasComponent;
import com.hana053.micropost.presentation.relateduserlist.followinglist.DaggerFollowingListComponent;
import com.hana053.micropost.presentation.relateduserlist.followinglist.FollowingListComponent;
import com.hana053.micropost.presentation.relateduserlist.followinglist.FollowingListModule;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.TestUtils;
import com.hana053.micropost.testing.shadows.ShadowFollowBtnServiceFactory;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.List;

import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(shadows = ShadowFollowBtnServiceFactory.class)
public class RelatedUserListFragmentTest extends RobolectricBaseTest {

    private final long userId = 1;
    private RelatedUserListFragment fragment;

    private final Observable<List<RelatedUser>> dummyUsers = Observable
            .just(new RelatedUser(1, "test", "test@test.com", "", 1))
            .toList();

    @Before
    public void setup() {
        final RelatedUserListInteractor interactor = getAppComponent().followingsInteractor();
        when(interactor.listRelatedUsers(userId, null)).thenReturn(dummyUsers);

        fragment = RelatedUserListFragment.newInstance(userId);
        SupportFragmentTestUtil.startFragment(fragment, TestActivity.class);
    }

    @Test
    public void shouldLoadPrevUsersOnScrolledToBottom() {
        fragment.relatedUserListService = spy(fragment.relatedUserListService);
        triggerScroll();
        verify(fragment.relatedUserListService).loadRelatedUsers(userId);
    }

    @Test
    public void shouldLoadUsersWhenActivityCreated() {
        advance();
        assertThat(fragment.userListAdapter.getItemCount(), is(1));
    }

    @Test
    public void shouldStartAndStopFollowBtnService() {
        verify(fragment.followBtnService).startObserving();
        fragment.onStop();
        verify(fragment.followBtnService).stopObserving();
    }

    private void triggerScroll() {
        final List<RelatedUser> users = Observable.range(1, 2)
                .map(id -> new RelatedUser(id, "test", "test@test.com", "", 1, new UserStats()))
                .toList()
                .toBlocking()
                .single();
        fragment.userListAdapter.addAll(0, users);
        TestUtils.populateItems(fragment.getBinding().userRecyclerView);
    }

    private static class TestActivity extends FragmentActivity implements ExplicitHasComponent<FollowingListComponent> {
        @Override
        public FollowingListComponent getComponent() {
            return DaggerFollowingListComponent.builder()
                    .appComponent(BaseApplication.component(this))
                    .activityModule(new ActivityModule(this))
                    .followingListModule(new FollowingListModule())
                    .build();
        }

        @Override
        public Class<FollowingListComponent> getComponentType() {
            return FollowingListComponent.class;
        }
    }

}