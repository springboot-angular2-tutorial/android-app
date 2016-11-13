package com.hana053.micropost.testing.shadows;

import com.hana053.micropost.presentation.core.services.AuthTokenService;
import com.hana053.micropost.presentation.core.services.ServiceModule_ProvideAuthTokenServiceFactory;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import static org.mockito.Mockito.mock;

@Implements(ServiceModule_ProvideAuthTokenServiceFactory.class)
public class ShadowAuthTokenServiceFactory {
    @Implementation
    public AuthTokenService get() {
        return mock(AuthTokenService.class);
    }
}
