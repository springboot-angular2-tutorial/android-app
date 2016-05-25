package com.hana053.micropost.presentation.top;

import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.shadows.ShadowNavigatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.mockito.Mockito.verify;

@Config(shadows = ShadowNavigatorFactory.class)
public class TopFragmentTest extends RobolectricBaseTest {

    private TopFragment fragment;

    private TextView loginBtn;
    private TextView signupBtn;

    @Before
    public void setup() {
        fragment = new TopFragment();
        SupportFragmentTestUtil.startFragment(fragment, TestActivity.class);

        loginBtn = fragment.getBinding().loginBtn;
        signupBtn = fragment.getBinding().signupBtn;

        fragment.getBinding().executePendingBindings();
    }

    @Test
    public void shouldMoveToSignupWhenClickedSignupBtn() {
        signupBtn.performClick();
        verify(fragment.navigator).navigateToSignup();
    }

    @Test
    public void shouldMoveToLoginWhenClickedLoginBtn() {
        loginBtn.performClick();
        verify(fragment.navigator).navigateToLogin();
    }

    private static class TestActivity extends FragmentActivity implements HasComponent<TopComponent> {
        @Override
        public TopComponent getComponent() {
            return DaggerTopComponent.builder()
                    .appComponent(BaseApplication.component(this))
                    .activityModule(new ActivityModule(this))
                    .build();
        }
    }

}