package com.hana053.micropost.presentation.core.services;

import com.hana053.micropost.domain.User;
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

    @Test
    public void shouldDetermineIsMyself() {
        authTokenService.setAuthToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNDc5NDUwNDY0fQ.Dy33qbg6EnP1bL2DmItMNGDEunrYP7-rzf586wxb2D-wW8WCsFrKdCeCU_ZHq_A7-kg_LxBykyaoG_26z-k9uA");

        User user1 = new User(1, "", "", "");
        assertThat(authTokenService.isMyself(user1), is(true));

        User user2 = new User(2, "", "", "");
        assertThat(authTokenService.isMyself(user2), is(false));
    }

}