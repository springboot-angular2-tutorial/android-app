package com.hana053.micropost.presentation.core.components.followbtn;

import com.hana053.micropost.interactors.RelationshipInteractor;
import com.hana053.micropost.presentation.core.services.HttpErrorHandler;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FollowBtnServiceTest extends RobolectricBaseTest {

    private final RelationshipInteractor relationshipInteractor = mock(RelationshipInteractor.class);

    private FollowBtnService followBtnService;

    @Before
    public void setup() {
        final HttpErrorHandler httpErrorHandler = mock(HttpErrorHandler.class);
        final CompositeSubscription subscriptions = new CompositeSubscription();
        followBtnService = new FollowBtnService(relationshipInteractor, httpErrorHandler, subscriptions);
    }

    @Test
    public void shouldUnfollowWhenClickedFollowBtn() {
        final FollowBtnViewModel viewModel = new FollowBtnViewModel(1, true);
        final FollowBtnClickEvent event = new FollowBtnClickEvent(viewModel);
        when(relationshipInteractor.unfollow(1)).thenReturn(Observable.empty());

        followBtnService.onEvent(event);

        advance();
        verify(relationshipInteractor).unfollow(1);
        assertThat(viewModel.isFollowedByMe.get(), is(false));
    }

    @Test
    public void shouldFollowWhenClickedFollowBtn() {
        final FollowBtnViewModel viewModel = new FollowBtnViewModel(1, false);
        final FollowBtnClickEvent event = new FollowBtnClickEvent(viewModel);
        when(relationshipInteractor.follow(1)).thenReturn(Observable.empty());

        followBtnService.onEvent(event);

        advance();
        verify(relationshipInteractor).follow(1);
        assertThat(viewModel.isFollowedByMe.get(), is(true));
    }

    @Test
    public void shouldSubscribeEventBus() {
        final EventBus eventBus = EventBus.getDefault();
        followBtnService.startObserving();
        assertThat(eventBus.isRegistered(followBtnService), is(true));
        followBtnService.stopObserving();
        assertThat(eventBus.isRegistered(followBtnService), is(false));
    }

}