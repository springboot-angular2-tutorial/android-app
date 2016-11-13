package com.hana053.micropost.presentation.usershow;

import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.AppComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                ActivityModule.class,
                UserShowModule.class,
        }
)
interface UserShowComponent {

    void inject(UserShowActivity activity);

    void inject(UserShowPostListFragment fragment);

    void inject(UserShowFragment fragment);

}
