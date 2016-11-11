package com.hana053.micropost.presentation.core.services;

import android.content.Intent;

import com.hana053.micropost.interactors.LoginInteractor;
import com.hana053.micropost.presentation.top.TopActivity;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

import okhttp3.HttpUrl;
import okhttp3.Protocol;
import retrofit2.Response;
import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class LoginServiceTest extends RobolectricBaseTest {

    private LoginService loginService;
    private AuthTokenService authTokenService;
    private LoginInteractor loginInteractor;

    @Before
    public void setup() {
        authTokenService = getAppComponent().authTokenService();
        loginService = getAppComponent().loginService();
        loginInteractor = getAppComponent().loginInteractor();
    }

    @Test
    public void shouldLogin() {
        when(loginInteractor.login(any(LoginInteractor.LoginRequest.class)))
                .thenReturn(dummyResponse());
        loginService.login("test@test.com", "secret").subscribe();
        advance();
        assertThat(authTokenService.getAuthToken(), is("my token"));
    }

    @Test
    public void shouldLogout() {
        authTokenService.setAuthToken("tmp");
        loginService.logout();
        final ShadowApplication shadow = Shadows.shadowOf(getTestApplication());
        final Intent intent = shadow.getNextStartedActivity();
        assertThat(intent.getFlags(), is(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        assertThat(TopActivity.class.getName(), is(intent.getComponent().getClassName()));
        assertThat(authTokenService.getAuthToken(), nullValue());
    }

    @Test
    public void shouldForceLogoutWhenAuthTokenIsEmpty() {
        assert authTokenService.getAuthToken() == null;
        assertThat(loginService.ensureAuthenticated(), is(false));
        final ShadowApplication shadow = Shadows.shadowOf(getTestApplication());
        final Intent intent = shadow.getNextStartedActivity();
        assertThat(TopActivity.class.getName(), is(intent.getComponent().getClassName()));
    }

    @Test
    public void shouldJustReturnTrueWhenAuthenticated() {
        authTokenService.setAuthToken("dummy");
        assertThat(loginService.ensureAuthenticated(), is(true));
    }

    private Observable<Response<String>> dummyResponse() {
        return Observable.just(Response.success("my token",
                new okhttp3.Response.Builder()
                        .code(200)
                        .protocol(Protocol.HTTP_1_1)
                        .request(new okhttp3.Request.Builder().url(HttpUrl.parse("http://localhost")).build())
                        .build()
        ));
    }

}