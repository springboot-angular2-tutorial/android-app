package com.hana053.micropost.presentation.signup;

import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = SignupModule.class)
public interface SignupComponent {

    void inject(SignupActivity signupActivity);

    void inject(SignupUtilityWrapper signupUtilityWrapper);

}
