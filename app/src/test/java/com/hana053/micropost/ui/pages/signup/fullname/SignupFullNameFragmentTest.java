package com.hana053.micropost.ui.pages.signup.fullname;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;
import com.hana053.micropost.ui.pages.signup.SignupCtrl;
import com.hana053.micropost.ui.pages.signup.SignupTestActivity;
import com.hana053.micropost.ui.pages.signup.SignupViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@SuppressLint("SetTextI18n")
public class SignupFullNameFragmentTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private SignupFullNameFragment fragment;

    private AppCompatEditText fullNameEditText;
    private Button nextBtn;
    private TextView invalidMsg;

    @Mock
    private SignupCtrl signupCtrl;

    @Before
    public void setup() {
        fragment = (SignupFullNameFragment) SignupFullNameFragment.newInstance(new SignupViewModel());
        SupportFragmentTestUtil.startFragment(fragment, SignupTestActivity.class);

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
        verify(signupCtrl).navigateToNewEmail();
    }

    private void applyChanges() {
        advance();
        fragment.getBinding().executePendingBindings();
    }

}