package com.hana053.micropost.presentation.main;

import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = MainModule.class)
@SuppressWarnings("WeakerAccess")
public interface MainComponent {

    void inject(MainActivity activity);

    void inject(MainFragment mainFragment);

}
