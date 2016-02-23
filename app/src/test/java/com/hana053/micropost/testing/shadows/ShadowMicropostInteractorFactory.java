package com.hana053.micropost.testing.shadows;

import com.hana053.micropost.interactors.InteractorModule_ProvideMicropostInteractorFactory;
import com.hana053.micropost.interactors.MicropostInteractor;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Implements(InteractorModule_ProvideMicropostInteractorFactory.class)
public class ShadowMicropostInteractorFactory {
    @Implementation
    public MicropostInteractor get() {
        MicropostInteractor interactor = mock(MicropostInteractor.class);
        when(interactor.create(any())).thenReturn(Observable.empty());
        return interactor;
    }
}
