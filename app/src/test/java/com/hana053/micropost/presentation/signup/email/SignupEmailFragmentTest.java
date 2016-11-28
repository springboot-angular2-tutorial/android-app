package com.hana053.micropost.presentation.signup.email;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hana053.micropost.presentation.signup.SignupCtrl;
import com.hana053.micropost.presentation.signup.SignupTestActivity;
import com.hana053.micropost.presentation.signup.SignupViewModel;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@SuppressLint("SetTextI18n")
public class SignupEmailFragmentTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private SignupEmailFragment fragment;
    private AppCompatEditText emailEditText;
    private TextView invalidMsg;
    private Button nextBtn;

    @Mock
    private SignupCtrl signupCtrl;

    @Before
    public void setup() {
        fragment = (SignupEmailFragment) SignupEmailFragment.newInstance(new SignupViewModel());
        SupportFragmentTestUtil.startFragment(fragment, SignupTestActivity.class);

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
        verify(signupCtrl).navigateToNewPassword();
    }

    private void applyChanges() {
        advance();
        fragment.getBinding().executePendingBindings();
    }
}