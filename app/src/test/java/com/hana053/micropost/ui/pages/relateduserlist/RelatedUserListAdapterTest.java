package com.hana053.micropost.ui.pages.relateduserlist;

import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hana053.micropost.domain.RelatedUser;
import com.hana053.micropost.services.AuthTokenService;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class RelatedUserListAdapterTest extends RobolectricBaseTest {

    private RelatedUserListAdapter userListAdapter;
    private AuthTokenService authTokenService = mock(AuthTokenService.class);

    @Before
    public void setup() {
        userListAdapter = new RelatedUserListAdapter(authTokenService);
    }

    @Test
    public void shouldGetLastItemId() {
        assertThat(userListAdapter.getLastItemId(), nullValue());
        final List<RelatedUser> relatedUsers = Stream.of(100, 101)
                .map(relationshipId -> new RelatedUser(1, "", "", "", relationshipId))
                .collect(Collectors.toList());
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
        final List<RelatedUser> relatedUsers = Stream.of(100)
                .map(relationshipId -> new RelatedUser(1, "", "", "", relationshipId))
                .collect(Collectors.toList());
        final boolean notified = userListAdapter.addAll(0, relatedUsers);
        assertThat(notified, is(true));
        assertThat(userListAdapter.getItemCount(), is(1));
    }

    @Test
    public void shouldNotNotifyWhenAddAllWithEmptyList() {
        assertThat(userListAdapter.addAll(0, Collections.emptyList()), is(false));
    }
}