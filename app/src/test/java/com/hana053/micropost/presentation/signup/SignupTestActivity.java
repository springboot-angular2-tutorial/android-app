package com.hana053.micropost.presentation.signup;

import android.support.v7.app.AppCompatActivity;

import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;

import static org.mockito.Mockito.mock;

public class SignupTestActivity extends AppCompatActivity implements HasComponent<SignupComponent> {
    public final SignupCtrl signupCtrl = mock(SignupCtrl.class);

    @Override
    public SignupComponent getComponent() {
        return DaggerSignupComponent.builder()
                .appComponent(BaseApplication.component(this))
                .activityModule(new ActivityModule(this))
                .signupModule(new SignupModule(signupCtrl))
                .build();
    }
}
