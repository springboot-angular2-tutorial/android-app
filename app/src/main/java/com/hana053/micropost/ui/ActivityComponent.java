package com.hana053.micropost.ui;

import com.hana053.micropost.ui.pages.top.TopComponent;

import dagger.Subcomponent;

@SuppressWarnings("WeakerAccess")
@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    TopComponent topComponent();

}
