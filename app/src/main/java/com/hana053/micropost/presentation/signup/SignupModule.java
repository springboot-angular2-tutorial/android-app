package com.hana053.micropost.presentation.signup;

import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Module;
import dagger.Provides;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Module
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SignupModule {

    private final SignupCtrl signupCtrl;

    @Provides
    @ActivityScope
    SignupCtrl provideSignupCtrl() {
        return signupCtrl;
    }

}
