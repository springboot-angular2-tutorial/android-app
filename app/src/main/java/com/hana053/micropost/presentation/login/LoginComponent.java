package com.hana053.micropost.presentation.login;

import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

    void inject(LoginFragment loginFragment);
}
