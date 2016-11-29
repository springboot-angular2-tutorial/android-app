package com.hana053.micropost.ui.pages.signup;

import com.hana053.micropost.interactors.UserInteractor;
import com.hana053.micropost.services.LoginService;
import com.hana053.micropost.ui.ActivityScope;

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
