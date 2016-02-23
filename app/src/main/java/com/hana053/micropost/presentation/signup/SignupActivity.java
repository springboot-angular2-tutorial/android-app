package com.hana053.micropost.presentation.signup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hana053.micropost.R;
import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;
import com.hana053.micropost.presentation.signup.email.SignupEmailFragment;
import com.hana053.micropost.presentation.signup.fullname.SignupFullNameFragment;
import com.hana053.micropost.presentation.signup.password.SignupPasswordFragment;

import org.parceler.Parcels;

import lombok.Getter;

public class SignupActivity extends AppCompatActivity implements SignupCtrl, HasComponent<SignupComponent> {

    private static final String KEY_VIEW_MODEL = "KEY_VIEW_MODEL";

    @Getter
    private SignupComponent component;

    private SignupViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            viewModel = new SignupViewModel();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, SignupFullNameFragment.newInstance(viewModel))
                    .commit();
        } else {
            viewModel = Parcels.unwrap(savedInstanceState.getParcelable(KEY_VIEW_MODEL));
        }

        component = DaggerSignupComponent.builder()
                .appComponent(BaseApplication.component(this))
                .activityModule(new ActivityModule(this))
                .signupModule(new SignupModule(this))
                .build();
        component.inject(this);

        setContentView(R.layout.layout_flat);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_VIEW_MODEL, Parcels.wrap(viewModel));
    }

    @Override
    public void onBackPressed() {
        int backStackCnt = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCnt == 0) {
            finish();
            return;
        }
        navigateToPrev();
    }

    @Override
    public void navigateToNewEmail() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SignupEmailFragment.newInstance(viewModel))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void navigateToNewPassword() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SignupPasswordFragment.newInstance(viewModel))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void navigateToPrev() {
        getSupportFragmentManager().popBackStack();
    }

}
