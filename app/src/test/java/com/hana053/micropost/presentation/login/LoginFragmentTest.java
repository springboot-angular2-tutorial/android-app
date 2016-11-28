package com.hana053.micropost.presentation.login;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;

import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;
import com.hana053.micropost.presentation.core.services.LoginService;
import com.hana053.micropost.presentation.core.services.Navigator;
import com.hana053.micropost.testing.EmptyResponseBody;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressLint("SetTextI18n")
public class LoginFragmentTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private AppCompatEditText emailEditText;
    private AppCompatEditText passwordEditText;
    private Button loginBtn;

    @Mock
    private LoginService loginService;

    @Mock
    private Navigator navigator;

    @Before
    public void setup() {
        final HttpException loginFailure = new HttpException(Response.error(401, new EmptyResponseBody()));
        when(loginService.login("test@test.com", "NG")).thenReturn(Observable.error(loginFailure));

        when(loginService.login("test@test.com", "OK")).thenReturn(Observable.just(null));

        LoginFragment fragment = new LoginFragment();
        SupportFragmentTestUtil.startFragment(fragment, TestActivity.class);

        emailEditText = fragment.getBinding().emailEditText;
        passwordEditText = fragment.getBinding().passwordEditText;
        loginBtn = fragment.getBinding().loginBtn;

        fragment.getBinding().executePendingBindings();
    }

    @Test
    public void shouldDisableBtnWhileFormIsInvalid() {
        assertThat(loginBtn.isEnabled(), is(false));
        emailEditText.setText("a");
        passwordEditText.setText("a");
        assertThat(loginBtn.isEnabled(), is(true));
    }

    @Test
    public void shouldShowErrorWhenClickedLoginBtnWithWrongEmailOrPassword() {
        emailEditText.setText("test@test.com");
        passwordEditText.setText("NG");
        loginBtn.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), is("Email or Password is wrong."));
    }

    @Test
    public void shouldNavigateToMainWhenLoginSuccess() {
        emailEditText.setText("test@test.com");
        passwordEditText.setText("OK");
        loginBtn.performClick();
//        verify(fragment.navigator).navigateToMain();
        verify(navigator).navigateToMain();
    }

    private static class TestActivity extends FragmentActivity implements HasComponent<LoginComponent> {
        @Override
        public LoginComponent getComponent() {
            return BaseApplication.component(this)
                    .activityComponent(new ActivityModule(this))
                    .loginComponent();
        }
    }

}