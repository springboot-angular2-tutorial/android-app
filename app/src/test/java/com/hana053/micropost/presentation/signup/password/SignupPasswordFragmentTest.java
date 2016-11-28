package com.hana053.micropost.presentation.signup.password;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hana053.micropost.presentation.core.services.Navigator;
import com.hana053.micropost.presentation.signup.SignupCtrl;
import com.hana053.micropost.presentation.signup.SignupService;
import com.hana053.micropost.presentation.signup.SignupTestActivity;
import com.hana053.micropost.presentation.signup.SignupViewModel;
import com.hana053.micropost.testing.EmptyResponseBody;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressLint("SetTextI18n")
public class SignupPasswordFragmentTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private SignupPasswordFragment fragment;

    private AppCompatEditText passwordEditText;
    private TextView invalidMsg;
    private Button nextBtn;

    @Mock
    private SignupService signupService;

    @Mock
    private Navigator navigator;

    @Mock
    private SignupCtrl signupCtrl;

    @Before
    public void setup() {
        fragment = (SignupPasswordFragment) SignupPasswordFragment.newInstance(new SignupViewModel());
        SupportFragmentTestUtil.startFragment(fragment, SignupTestActivity.class);

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
    public void shouldFinishSignupWhenEmailIsUnique() {
        when(signupService.signup(any()))
                .thenReturn(Observable.just(null));
        nextBtn.performClick();
        applyChanges();
        verify(navigator).navigateToMain();
    }

    @Test
    public void shouldNavigateToNewEmailWhenEmailIsNotUnique() {
        when(signupService.signup(any()))
                .thenReturn(Observable.error(new HttpException(Response.error(400, new EmptyResponseBody()))));
        nextBtn.performClick();
        applyChanges();
        verify(signupCtrl).navigateToPrev();
    }

    private void applyChanges() {
        advance();
        fragment.getBinding().executePendingBindings();
    }
}