package com.hana053.micropost.presentation.usershow;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.domain.User;
import com.hana053.micropost.interactors.UserMicropostInteractor;
import com.hana053.micropost.presentation.usershow.UserShowPostListFragment;
import com.hana053.micropost.presentation.usershow.UserShowTestActivity;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.List;

import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserShowPostListFragmentTest extends RobolectricBaseTest {

    private final User user = new User(1, "", "", "");
    private UserShowPostListFragment fragment;

    private final Observable<List<Micropost>> dummyPosts = Observable
            .just(new Micropost(1, "content", 0, null))
            .toList();

    @Before
    public void setup() {
        final UserMicropostInteractor interactor = getAppComponent().userMicropostInteractor();
        when(interactor.loadPrevPosts(user.getId(), null)).thenReturn(dummyPosts);

        fragment = UserShowPostListFragment.newInstance(user.getId());
        SupportFragmentTestUtil.startFragment(fragment, UserShowTestActivity.class);
    }

    @Test
    public void shouldLoadPrevPostsOnScrolledToBottom() {
        fragment.userShowService = spy(fragment.userShowService);
        triggerScroll();
        verify(fragment.userShowService).loadPosts(user.getId());
    }

    @Test
    public void shouldLoadPostsWhenActivityCreated() {
        advance();
        assertThat(fragment.postListAdapter.getItemCount(), is(1));
    }

    private void triggerScroll() {
        final List<Micropost> posts = Observable.range(1, 2)
                .map(id -> new Micropost(id, "content", 0, user))
                .toList()
                .toBlocking()
                .single();
        fragment.postListAdapter.addAll(0, posts);
        TestUtils.populateItems(fragment.getBinding().postRecyclerView);
    }

}