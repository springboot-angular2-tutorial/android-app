package com.hana053.micropost.ui.pages.main;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.interactors.FeedInteractor;
import com.hana053.micropost.ui.components.micropostlist.PostListAdapter;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class MainServiceTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    @Mock
    private FeedInteractor feedInteractor;

    private final PostListAdapter postListAdapter = new PostListAdapter();

    private MainService mainService;

    @Before
    public void setup() {
        when(feedInteractor.loadNextFeed(any())).thenReturn(Observable.empty());
        when(feedInteractor.loadPrevFeed(any())).thenReturn(Observable.empty());
        mainService = new MainServiceImpl(feedInteractor, postListAdapter);
    }

    @Test
    public void shouldLoadNextFeed() {
        final List<Micropost> posts = Stream.of(100, 101)
                .map(id -> new Micropost(id, "", 0, null))
                .collect(Collectors.toList());
        postListAdapter.addAll(0, posts);
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
        final List<Micropost> posts = Stream.of(100, 101)
                .map(id -> new Micropost(id, "", 0, null))
                .collect(Collectors.toList());
        postListAdapter.addAll(0, posts);
        final Observable<List<Micropost>> response = Observable.just(102)
                .map(id -> new Micropost(id, "", 0, null))
                .toList();
        when(feedInteractor.loadPrevFeed(101L)).thenReturn(response);

        mainService.loadPrevFeed().subscribe();
        advance();

        assertThat(postListAdapter.getItemCount(), is(3));
    }

}