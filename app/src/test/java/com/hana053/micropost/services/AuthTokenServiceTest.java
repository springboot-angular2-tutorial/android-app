package com.hana053.micropost.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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


}