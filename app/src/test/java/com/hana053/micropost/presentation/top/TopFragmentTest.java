package com.hana053.micropost.presentation.top;

import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;
import com.hana053.micropost.presentation.core.services.Navigator;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.mockito.Mockito.verify;

public class TopFragmentTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private TextView loginBtn;
    private TextView signupBtn;

    @Mock
    Navigator navigator;

    @Before
    public void setup() {
        TopFragment fragment = new TopFragment();
        SupportFragmentTestUtil.startFragment(fragment, TestActivity.class);

        loginBtn = fragment.getBinding().loginBtn;
        signupBtn = fragment.getBinding().signupBtn;

        fragment.getBinding().executePendingBindings();
    }

    @Test
    public void shouldMoveToSignupWhenClickedSignupBtn() {
        signupBtn.performClick();
        verify(navigator).navigateToSignup();
    }

    @Test
    public void shouldMoveToLoginWhenClickedLoginBtn() {
        loginBtn.performClick();
        verify(navigator).navigateToLogin();
    }

    private static class TestActivity extends FragmentActivity implements HasComponent<TopComponent> {
        @Override
        public TopComponent getComponent() {
            return BaseApplication.component(this)
                    .activityComponent(new ActivityModule(this))
                    .topComponent();
        }
    }

}