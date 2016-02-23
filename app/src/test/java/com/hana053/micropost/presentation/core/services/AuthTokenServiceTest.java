package com.hana053.micropost.presentation.core.services;

import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class AuthTokenServiceTest extends RobolectricBaseTest {

    private AuthTokenService authTokenService;

    @Before
    public void setup() {
        authTokenService = getAppComponent().authTokenService();
    }

    @Test
    public void shouldSaveAuthToken() {
        authTokenService.setAuthToken("my token");
        assertThat(authTokenService.getAuthToken(), is("my token"));
    }

    @Test
    public void shouldClearAuthToken() {
        authTokenService.setAuthToken("my token");
        authTokenService.clearAuthToken();
        assertThat(authTokenService.getAuthToken(), nullValue());
    }

}