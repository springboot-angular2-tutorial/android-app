package com.hana053.micropost.ui.pages.signup;

import com.hana053.micropost.domain.User;
import com.hana053.micropost.interactors.UserInteractor;
import com.hana053.micropost.services.LoginService;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignupServiceTest extends RobolectricBaseTest {

    private final UserInteractor userInteractor = mock(UserInteractor.class);
    private final LoginService loginService = mock(LoginService.class);
    private final SignupService signupService = new SignupServiceImpl(userInteractor, loginService);

    @Test
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