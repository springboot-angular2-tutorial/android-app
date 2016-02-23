package com.hana053.micropost.presentation.signup.email;

import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hana053.micropost.presentation.signup.SignupTestActivity;
import com.hana053.micropost.presentation.signup.SignupViewModel;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class SignupEmailFragmentTest extends RobolectricBaseTest {

    private SignupEmailFragment fragment;
    private AppCompatEditText emailEditText;
    private TextView invalidMsg;
    private Button nextBtn;
    private SignupTestActivity activity;

    @Before
    public void setup() {
        fragment = (SignupEmailFragment) SignupEmailFragment.newInstance(new SignupViewModel());
        SupportFragmentTestUtil.startFragment(fragment, SignupTestActivity.class);
        activity = (SignupTestActivity) fragment.getActivity();

        emailEditText = fragment.getBinding().email;
        nextBtn = fragment.getBinding().nextBtn;
        invalidMsg = fragment.getBinding().emailInvalid;

        applyChanges();
    }

    @Test
    public void shouldInputEmailWithValidation() {
        assertThat(nextBtn.isEnabled(), is(false));
        assertThat(invalidMsg.getVisibility(), is(View.GONE));

        emailEditText.setText("t");
        applyChanges();

        assertThat(nextBtn.isEnabled(), is(false));
        assertThat(invalidMsg.getVisibility(), is(View.VISIBLE));

        emailEditText.setText("test@test.com");
        applyChanges();

        assertThat(nextBtn.isEnabled(), is(true));
        assertThat(invalidMsg.getVisibility(), is(View.GONE));
    }

    @Test
    public void shouldNavigateToNewPassword() {
        emailEditText.setText("test@test.com");
        applyChanges();
        nextBtn.performClick();
        verify(activity.signupCtrl).navigateToNewPassword();
    }

    private void applyChanges() {
        advance();
        fragment.getBinding().executePendingBindings();
    }
}