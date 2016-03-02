package com.hana053.micropost.presentation.relateduserlist;

import android.support.v7.widget.RecyclerView;

import com.hana053.micropost.domain.RelatedUser;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class RelatedUserListAdapterTest extends RobolectricBaseTest {

    private RelatedUserListAdapter userListAdapter;

    @Before
    public void setup() {
        userListAdapter = new RelatedUserListAdapter();
    }

    @Test
    public void shouldGetLastItemId() {
        assertThat(userListAdapter.getLastItemId(), nullValue());
        final List<RelatedUser> relatedUsers = Observable.range(100, 2)
                .map(relationshipId -> new RelatedUser(1, "", "", relationshipId))
                .toList()
                .toBlocking()
                .single();
        userListAdapter.addAll(0, relatedUsers);
        assertThat(userListAdapter.getLastItemId(), is(101L));
    }

    @Test
    public void shouldAddAll() {
        userListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                assertThat(positionStart, is(0));
                assertThat(itemCount, is(1));
            }
        });
        final List<RelatedUser> relatedUsers = Observable.just(100)
                .map(relationshipId -> new RelatedUser(1, "", "", relationshipId))
                .toList()
                .toBlocking()
                .single();
        final boolean notified = userListAdapter.addAll(0, relatedUsers);
        assertThat(notified, is(true));
        assertThat(userListAdapter.getItemCount(), is(1));
    }

    @Test
    public void shouldNotNotifyWhenAddAllWithEmptyList() {
        assertThat(userListAdapter.addAll(0, Collections.emptyList()), is(false));
    }
}