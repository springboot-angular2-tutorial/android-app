package com.hana053.micropost.presentation.core.components.micropostlist;

import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class PostListAdapterTest extends RobolectricBaseTest {

    private PostListAdapter postListAdapter;

    @Before
    public void setup() {
        postListAdapter = new PostListAdapter();
    }

    @Test
    public void shouldGetFirstItemId() {
        assertThat(postListAdapter.getFirstItemId(), nullValue());
        final List<Micropost> posts = Stream.of(100, 101)
                .map(id -> new Micropost(id, "", 0, null))
                .collect(Collectors.toList());
        postListAdapter.addAll(0, posts);
        assertThat(postListAdapter.getFirstItemId(), is(100L));
    }

    @Test
    public void shouldGetLastItemId() {
        assertThat(postListAdapter.getFirstItemId(), nullValue());
        final List<Micropost> posts = Stream.of(100, 101)
                .map(id -> new Micropost(id, "", 0, null))
                .collect(Collectors.toList());
        postListAdapter.addAll(0, posts);
        assertThat(postListAdapter.getLastItemId(), is(101L));
    }

    @Test
    public void shouldAddAll() {
        postListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                assertThat(positionStart, is(0));
                assertThat(itemCount, is(1));
            }
        });
        final List<Micropost> posts = Stream.of(100)
                .map(id -> new Micropost(id, "", 0, null))
                .collect(Collectors.toList());
        final boolean notified = postListAdapter.addAll(0, posts);
        assertThat(notified, is(true));
        assertThat(postListAdapter.getItemCount(), is(1));
    }

    @Test
    public void shouldNotNotifyWhenAddAllWithEmptyList() {
        assertThat(postListAdapter.addAll(0, Collections.emptyList()), is(false));
    }
}
