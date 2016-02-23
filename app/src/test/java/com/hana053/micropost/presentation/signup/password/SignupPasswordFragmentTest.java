package com.hana053.micropost.presentation.signup.password;

import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hana053.micropost.presentation.signup.SignupService;
import com.hana053.micropost.presentation.signup.SignupTestActivity;
import com.hana053.micropost.presentation.signup.SignupViewModel;
import com.hana053.micropost.testing.EmptyResponseBody;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.shadows.ShadowNavigatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignupPasswordFragmentTest extends RobolectricBaseTest {

    private SignupPasswordFragment fragment;
    private SignupTestActivity activity;

    private AppCompatEditText passwordEditText;
    private TextView invalidMsg;
    private Button nextBtn;

    @Before
    public void setup() {
        fragment = (SignupPasswordFragment) SignupPasswordFragment.newInstance(new SignupViewModel());
        SupportFragmentTestUtil.startFragment(fragment, SignupTestActivity.class);
        activity = (SignupTestActivity) fragment.getActivity();

        passwordEditText = fragment.getBinding().password;
        nextBtn = fragment.getBinding().nextBtn;
        invalidMsg = fragment.getBinding().passwordInvalid;

        applyChanges();
    }

    @Test
    public void shouldInputPasswordWithValidation() {
        assertThat(nextBtn.isEnabled(), is(false));
        assertThat(invalidMsg.getVisibility(), is(View.GONE));

        passwordEditText.setText("1");
        applyChanges();

        assertThat(nextBtn.isEnabled(), is(false));
        assertThat(invalidMsg.getVisibility(), is(View.VISIBLE));

        passwordEditText.setText("12345678");
        applyChanges();

        assertThat(nextBtn.isEnabled(), is(true));
        assertThat(invalidMsg.getVisibility(), is(View.GONE));
    }

    @Test
    @Config(shadows = ShadowNavigatorFactory.class)
    public void shouldFinishSignupWhenEmailIsUnique() {
        fragment.signupService = mock(SignupService.class);
        when(fragment.signupService.signup(any()))
                .thenReturn(Observable.just(Response.success(null)));
        nextBtn.performClick();
        applyChanges();
        verify(fragment.navigator).navigateToMain();
    }

    @Test
    public void shouldNavigateToNewEmailWhenEmailIsNotUnique() {
        fragment.signupService = mock(SignupService.class);
        when(fragment.signupService.signup(any()))
                .thenReturn(Observable.error(new HttpException(Response.error(400, new EmptyResponseBody()))));
        nextBtn.performClick();
        applyChanges();
        verify(activity.signupCtrl).navigateToPrev();
    }

    private void applyChanges() {
        advance();
        fragment.getBinding().executePendingBindings();
    }
}