package com.hana053.micropost.presentation.usershow;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.interactors.UserMicropostInteractor;
import com.hana053.micropost.presentation.core.components.micropostlist.PostListAdapter;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class UserShowServiceTest extends RobolectricBaseTest {

    private UserShowService userShowService;
    private UserMicropostInteractor userMicropostInteractor;
    private final PostListAdapter postAdapter = new PostListAdapter();

    @Before
    public void setup() {
        userMicropostInteractor = getAppComponent().userMicropostInteractor();
        userShowService = new UserShowService(userMicropostInteractor, postAdapter);
    }

    @Test
    public void shouldLoadPosts() {
        final Observable<List<Micropost>> dummyPosts = Observable
                .just(new Micropost(1, "content", 0, null))
                .toList();
        when(userMicropostInteractor.loadPrevPosts(1, null)).thenReturn(dummyPosts);

        userShowService.loadPosts(1).subscribe();
        advance();

        assertThat(postAdapter.getItemCount(), is(1));
    }

}