package com.hana053.micropost.ui.pages.signup;

import com.hana053.micropost.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = SignupModule.class)
public interface SignupComponent {

    void inject(SignupActivity signupActivity);

    void inject(SignupUtilityWrapper signupUtilityWrapper);

}
