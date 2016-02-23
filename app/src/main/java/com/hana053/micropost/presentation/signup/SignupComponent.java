package com.hana053.micropost.presentation.signup;

import com.hana053.micropost.databinding.SignupEmailBinding;
import com.hana053.micropost.databinding.SignupFullNameBinding;
import com.hana053.micropost.databinding.SignupPasswordBinding;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.AppComponent;
import com.hana053.micropost.presentation.signup.email.SignupEmailFragment;
import com.hana053.micropost.presentation.signup.fullname.SignupFullNameFragment;
import com.hana053.micropost.presentation.signup.password.SignupPasswordFragment;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {AppComponent.class},
        modules = {
                ActivityModule.class,
                SignupModule.class
        }
)
public interface SignupComponent {

    void inject(SignupActivity signupActivity);

    void inject(SignupFullNameFragment fragment);

    void inject(SignupEmailFragment fragment);

    void inject(SignupPasswordFragment fragment);

    // required. If removed, dagger will cause an error
    void dummy1(SignupBaseFragment<SignupFullNameBinding> fragment);

    // required. If removed, dagger will cause an error
    void dummy2(SignupBaseFragment<SignupEmailBinding> fragment);

    // required. If removed, dagger will cause an error
    void dummy3(SignupBaseFragment<SignupPasswordBinding> fragment);

}
