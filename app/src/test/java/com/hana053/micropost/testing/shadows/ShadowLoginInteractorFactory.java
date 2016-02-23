package com.hana053.micropost.testing.shadows;

import com.hana053.micropost.interactors.InteractorModule_ProvideLoginInteractorFactory;
import com.hana053.micropost.interactors.LoginInteractor;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Implements(InteractorModule_ProvideLoginInteractorFactory.class)
public class ShadowLoginInteractorFactory {
    @Implementation
    public LoginInteractor get() {
        LoginInteractor interactor = mock(LoginInteractor.class);
        when(interactor.login(any())).thenReturn(Observable.empty());
        return interactor;
    }
}
