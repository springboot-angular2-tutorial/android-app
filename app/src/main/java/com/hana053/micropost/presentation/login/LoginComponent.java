package com.hana053.micropost.presentation.login;

import com.hana053.micropost.databinding.LoginBinding;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.AppComponent;
import com.hana053.micropost.presentation.core.base.BaseFragment;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {AppComponent.class},
        modules = {
                ActivityModule.class,
        }
)
interface LoginComponent {

    void inject(LoginActivity loginActivity);

    void inject(LoginFragment loginFragment);

    // It's required. If it was removed, dagger will cause an error
    void inject(BaseFragment<LoginViewModel, LoginBinding> fragment);
}
