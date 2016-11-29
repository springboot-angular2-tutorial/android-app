package com.hana053.micropost.ui.pages.main;

import com.hana053.micropost.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = MainModule.class)
@SuppressWarnings("WeakerAccess")
public interface MainComponent {

    void inject(MainActivity activity);

    void inject(MainFragment mainFragment);

}
