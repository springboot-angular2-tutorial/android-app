package com.hana053.micropost.testing.shadows;

import com.hana053.micropost.interactors.FeedInteractor;
import com.hana053.micropost.interactors.InteractorModule_ProvideFeedInteractorFactory;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Implements(InteractorModule_ProvideFeedInteractorFactory.class)
public class ShadowFeedInteractorFactory {
    @Implementation
    public FeedInteractor get() {
        FeedInteractor interactor = mock(FeedInteractor.class);
        when(interactor.loadNextFeed(any())).thenReturn(Observable.empty());
        when(interactor.loadPrevFeed(any())).thenReturn(Observable.empty());
        return interactor;
    }
}
