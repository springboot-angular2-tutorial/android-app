package com.hana053.micropost.ui.pages.signup;

import android.support.v7.app.AppCompatActivity;

import com.hana053.micropost.BaseApplication;
import com.hana053.micropost.di.HasComponent;
import com.hana053.micropost.ui.ActivityModule;

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
