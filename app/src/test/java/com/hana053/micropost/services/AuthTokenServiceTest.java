package com.hana053.micropost.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hana053.micropost.domain.User;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class AuthTokenServiceTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RuntimeEnvironment.application);

    private AuthTokenService authTokenService;

    @Before
    public void setup() {
        authTokenService = new AuthTokenServiceImpl(sharedPreferences);
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