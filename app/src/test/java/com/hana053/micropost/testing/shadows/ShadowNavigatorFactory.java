package com.hana053.micropost.testing.shadows;

import com.hana053.micropost.presentation.core.services.Navigator;
import com.hana053.micropost.presentation.core.services.Navigator_Factory;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import static org.mockito.Mockito.mock;

@Implements(Navigator_Factory.class)
public class ShadowNavigatorFactory {
    @Implementation
    public Navigator get() {
        return mock(Navigator.class);
    }
}
