package com.hana053.micropost.ui.pages.top;

import com.hana053.micropost.ui.ActivityModule;
import com.hana053.micropost.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface TopComponent {

    void inject(TopActivity activity);

}
