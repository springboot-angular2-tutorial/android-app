package com.hana053.micropost.testing.shadows;

import com.hana053.micropost.presentation.core.components.followbtn.FollowBtnService;
import com.hana053.micropost.presentation.core.components.followbtn.FollowBtnService_Factory;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import static org.mockito.Mockito.mock;

@Implements(FollowBtnService_Factory.class)
public class ShadowFollowBtnServiceFactory {
    @Implementation
    public FollowBtnService get() {
        return mock(FollowBtnService.class);
    }
}
