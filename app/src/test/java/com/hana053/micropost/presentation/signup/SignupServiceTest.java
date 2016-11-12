package com.hana053.micropost.presentation.signup;

import com.hana053.micropost.domain.User;
import com.hana053.micropost.interactors.UserInteractor;
import com.hana053.micropost.presentation.core.services.LoginService;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.shadows.ShadowLoginServiceFactory;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignupServiceTest extends RobolectricBaseTest {

    private SignupService signupService;
    private UserInteractor userInteractor;
    private LoginService loginService;

    @Before
    public void setup() {
        loginService = getAppComponent().loginService();
        userInteractor = getAppComponent().userInteractor();
        signupService = new SignupService(userInteractor, loginService);
    }

    @Test
    @Config(shadows = ShadowLoginServiceFactory.class)
    public void shouldSignup() {
        final TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
        final UserInteractor.SignupRequest request = UserInteractor.SignupRequest.builder()
                .email("test@test.com")
                .password("secret123")
                .name("test user")
                .build();
        final Observable<User> signupResponse = Observable.just(new User(1, "test user", "test@test.com", ""));
        when(userInteractor.create(request)).thenReturn(signupResponse);
        when(loginService.login("test@test.com", "secret123")).thenReturn(Observable.empty());

        signupService.signup(request).subscribe(testSubscriber);
        advance();

        testSubscriber.assertNoErrors();
        verify(loginService).login("test@test.com", "secret123");
    }

}