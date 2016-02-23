package com.hana053.micropost.testing.shadows;

import com.hana053.micropost.presentation.core.services.LoginService;
import com.hana053.micropost.presentation.core.services.LoginService_Factory;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Implements(LoginService_Factory.class)
public class ShadowLoginServiceFactory {
    @Implementation
    public LoginService get() {
        final LoginService mock = mock(LoginService.class);
        when(mock.ensureAuthenticated()).thenReturn(true);
        return mock;
    }
}
