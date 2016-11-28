package com.hana053.micropost.presentation.usershow;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.domain.User;
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

public class UserShowPostListFragmentTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private final User user = new User(1, "", "", "");
    private UserShowPostListFragment fragment;

    private final Observable<List<Micropost>> dummyPosts = Observable
            .just(new Micropost(1, "content", 0, null))
            .toList();

    @Mock
    private UserShowService userShowService;

    @Before
    public void setup() {
        when(userShowService.loadPosts(user.getId())).thenReturn(dummyPosts);

        fragment = UserShowPostListFragment.newInstance(user.getId());
        SupportFragmentTestUtil.startFragment(fragment, UserShowTestActivity.class);
    }

    @Test
    public void shouldLoadPrevPostsOnScrolledToBottom() {
        triggerScroll();
        verify(userShowService, times(2)).loadPosts(user.getId());
    }

    @Test
    public void shouldLoadPostsWhenActivityCreated() {
        verify(userShowService).loadPosts(user.getId());
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