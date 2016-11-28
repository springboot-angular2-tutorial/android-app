package com.hana053.micropost.presentation.top;

import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent
public interface TopComponent {

    void inject(TopActivity activity);

    void inject(TopFragment fragment);
}
