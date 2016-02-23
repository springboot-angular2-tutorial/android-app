package com.hana053.micropost.testing.shadows;

import com.hana053.micropost.interactors.InteractorModule_ProvideUserInteractorFactory;
import com.hana053.micropost.interactors.UserInteractor;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Implements(InteractorModule_ProvideUserInteractorFactory.class)
public class ShadowUserInteractorFactory {

    @Implementation
    public UserInteractor get() {
        UserInteractor interactor = mock(UserInteractor.class);
        when(interactor.create(any())).thenReturn(Observable.empty());
        when(interactor.listFollowings(anyLong(), anyLong())).thenReturn(Observable.empty());
        when(interactor.listFollowers(anyLong(), anyLong())).thenReturn(Observable.empty());
        return interactor;
    }

}
