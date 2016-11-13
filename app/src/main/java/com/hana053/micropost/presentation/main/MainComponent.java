package com.hana053.micropost.presentation.main;

import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.AppComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {AppComponent.class},
        modules = {
                ActivityModule.class,
                MainModule.class
        }
)
interface MainComponent {

    void inject(MainActivity activity);

    void inject(MainFragment mainFragment);

}
