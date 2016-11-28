package com.hana053.micropost.presentation.signup;

import android.support.v7.app.AppCompatActivity;

import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;

public class SignupTestActivity extends AppCompatActivity implements SignupCtrl, HasComponent<SignupComponent> {
    @Override
    public SignupComponent getComponent() {
        return BaseApplication.component(this)
                .activityComponent(new ActivityModule(this))
                .signupComponent(new SignupModule(this));
    }

    @Override
    public void navigateToNewEmail() {
    }

    @Override
    public void navigateToNewPassword() {
    }

    @Override
    public void navigateToPrev() {
    }
}
