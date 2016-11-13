package com.hana053.micropost.presentation.relateduserlist;

import com.hana053.micropost.domain.RelatedUser;
import com.hana053.micropost.interactors.RelatedUserListInteractor;
import com.hana053.micropost.presentation.core.services.AuthTokenService;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class RelatedUserListServiceTest extends RobolectricBaseTest {

    @Mock
    private RelatedUserListInteractor interactor;

    @Mock
    private AuthTokenService authTokenService;

    private final RelatedUserListAdapter userListAdapter = new RelatedUserListAdapter(authTokenService);

    private RelatedUserListService relatedUserListService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        relatedUserListService = new RelatedUserListService(interactor, userListAdapter);
    }

    @Test
    public void shouldLoadRelatedUsers() {
        final List<RelatedUser> users = Observable.range(100, 2)
                .map(relationshipId -> new RelatedUser(1, "test", "test@test.com", "", relationshipId))
                .toList()
                .toBlocking()
                .single();
        userListAdapter.addAll(0, users);
        final Observable<List<RelatedUser>> response = Observable.just(102)
                .map(relationshipId -> new RelatedUser(1, "test", "test@test.com", "", relationshipId))
                .toList();
        when(interactor.listRelatedUsers(1, 101L)).thenReturn(response);

        relatedUserListService.loadRelatedUsers(1).subscribe();
        advance();

        assertThat(userListAdapter.getItemCount(), is(3));
    }

}