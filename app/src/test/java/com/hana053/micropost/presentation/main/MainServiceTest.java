package com.hana053.micropost.presentation.main;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.interactors.FeedInteractor;
import com.hana053.micropost.presentation.core.components.micropostlist.PostListAdapter;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class MainServiceTest extends RobolectricBaseTest {

    private FeedInteractor feedInteractor;
    private final PostListAdapter postListAdapter = new PostListAdapter();
    private MainService mainService;

    @Before
    public void setup() {
        feedInteractor = getAppComponent().feedInteractor();
        mainService = new MainService(feedInteractor, postListAdapter);
    }

    @Test
    public void shouldLoadNextFeed() {
        final List<Micropost> microposts = Observable.range(100, 2)
                .map(id -> new Micropost(id, "", 0, null))
                .toList()
                .toBlocking()
                .single();
        postListAdapter.addAll(0, microposts);
        final Observable<List<Micropost>> response = Observable.just(99)
                .map(id -> new Micropost(id, "", 0, null))
                .toList();
        when(feedInteractor.loadNextFeed(100L)).thenReturn(response);

        mainService.loadNextFeed().subscribe();
        advance();

        assertThat(postListAdapter.getItemCount(), is(3));
    }

    @Test
    public void shouldLoadPrevFeed() {
        final List<Micropost> microposts = Observable.range(100, 2)
                .map(id -> new Micropost(id, "", 0, null))
                .toList()
                .toBlocking()
                .single();
        postListAdapter.addAll(0, microposts);
        final Observable<List<Micropost>> response = Observable.just(102)
                .map(id -> new Micropost(id, "", 0, null))
                .toList();
        when(feedInteractor.loadPrevFeed(101L)).thenReturn(response);

        mainService.loadPrevFeed().subscribe();
        advance();

        assertThat(postListAdapter.getItemCount(), is(3));
    }

}