package com.hana053.micropost.presentation.top;

import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.AppComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {AppComponent.class},
        modules = {
                ActivityModule.class,
        }
)
interface TopComponent {

    void inject(TopActivity activity);

    void inject(TopFragment fragment);
}
