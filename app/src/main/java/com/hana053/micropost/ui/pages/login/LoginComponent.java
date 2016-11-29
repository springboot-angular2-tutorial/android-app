package com.hana053.micropost.ui.pages.login;

import com.hana053.micropost.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

    void inject(LoginFragment loginFragment);
}
