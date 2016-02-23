package com.hana053.micropost.presentation.signup.fullname;

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

public class SignupFullNameFragmentTest extends RobolectricBaseTest {

    private SignupFullNameFragment fragment;
    private SignupTestActivity activity;

    private AppCompatEditText fullNameEditText;
    private Button nextBtn;
    private TextView invalidMsg;

    @Before
    public void setup() {
        fragment = (SignupFullNameFragment) SignupFullNameFragment.newInstance(new SignupViewModel());
        SupportFragmentTestUtil.startFragment(fragment, SignupTestActivity.class);
        activity = (SignupTestActivity) fragment.getActivity();

        fullNameEditText = fragment.getBinding().fullName;
        nextBtn = fragment.getBinding().nextBtn;
        invalidMsg = fragment.getBinding().fullNameInvalid;

        applyChanges();
    }

    @Test
    public void shouldInputFullNameWithValidation() {
        assertThat(nextBtn.isEnabled(), is(false));
        assertThat(invalidMsg.getVisibility(), is(View.GONE));

        fullNameEditText.setText("123");
        applyChanges();

        assertThat(nextBtn.isEnabled(), is(false));
        assertThat(invalidMsg.getVisibility(), is(View.VISIBLE));

        fullNameEditText.setText("1234");
        applyChanges();

        assertThat(nextBtn.isEnabled(), is(true));
        assertThat(invalidMsg.getVisibility(), is(View.GONE));
    }

    @Test
    public void shouldNavigateToNewEmail() {
        fullNameEditText.setText("1234");
        applyChanges();
        nextBtn.performClick();
        verify(activity.signupCtrl).navigateToNewEmail();
    }

    private void applyChanges() {
        advance();
        fragment.getBinding().executePendingBindings();
    }

}