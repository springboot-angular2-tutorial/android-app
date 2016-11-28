package com.hana053.micropost.presentation.core.services;

import android.content.Intent;

import com.hana053.micropost.interactors.LoginInteractor;
import com.hana053.micropost.presentation.top.TopActivity;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginServiceTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    @Mock
    private LoginInteractor loginInteractor;

    @Mock
    private AuthTokenService authTokenService;

    private LoginService loginService;

    @Before
    public void setup() {
        loginService = new LoginServiceImpl(loginInteractor, authTokenService, RuntimeEnvironment.application);
    }

    @Test
    public void shouldLogin() {
        final Observable<LoginInteractor.LoginResponse> dummyResponse
                = Observable.just(new LoginInteractor.LoginResponse("my token"));
        when(loginInteractor.login(any(LoginInteractor.LoginRequest.class)))
                .thenReturn(dummyResponse);
        loginService.login("test@test.com", "secret").subscribe();
        advance();
        verify(authTokenService).setAuthToken("my token");
    }

    @Test
    public void shouldLogout() {
        loginService.logout();
        verify(authTokenService).clearAuthToken();
        final ShadowApplication shadow = Shadows.shadowOf(RuntimeEnvironment.application);
        final Intent intent = shadow.getNextStartedActivity();
        assertThat(intent.getFlags(), is(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        assertThat(TopActivity.class.getName(), is(intent.getComponent().getClassName()));
    }

    @Test
    public void shouldForceLogoutWhenAuthTokenIsEmpty() {
        assertThat(loginService.ensureAuthenticated(), is(false));
        final ShadowApplication shadow = Shadows.shadowOf(RuntimeEnvironment.application);
        final Intent intent = shadow.getNextStartedActivity();
        assertThat(TopActivity.class.getName(), is(intent.getComponent().getClassName()));
    }

    @Test
    public void shouldJustReturnTrueWhenAuthenticated() {
        when(authTokenService.getAuthToken()).thenReturn("test token");
        assertThat(loginService.ensureAuthenticated(), is(true));
    }

}