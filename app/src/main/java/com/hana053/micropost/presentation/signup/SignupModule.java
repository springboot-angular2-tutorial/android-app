package com.hana053.micropost.presentation.signup;

import com.hana053.micropost.interactors.UserInteractor;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.services.LoginService;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;

@SuppressWarnings("WeakerAccess")
@Module
@AllArgsConstructor
public class SignupModule {

    private final SignupCtrl signupCtrl;

    @Provides
    @ActivityScope
    public SignupCtrl provideSignupCtrl() {
        return signupCtrl;
    }

    @Provides
    @ActivityScope
    public SignupService provideSignupService(UserInteractor userInteractor,
                                              LoginService loginService) {
        return new SignupServiceImpl(userInteractor, loginService);
    }

}
