package com.hana053.micropost.testing.shadows;

import com.hana053.micropost.interactors.InteractorModule_ProvideUserMicropostInteractorFactory;
import com.hana053.micropost.interactors.UserMicropostInteractor;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import rx.Observable;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Implements(InteractorModule_ProvideUserMicropostInteractorFactory.class)
public class ShadowUserMicropostInteractorFactory {
    @Implementation
    public UserMicropostInteractor get() {
        UserMicropostInteractor interactor = mock(UserMicropostInteractor.class);
        when(interactor.loadPrevPosts(anyLong(), anyLong())).thenReturn(Observable.empty());
        return interactor;
    }
}
