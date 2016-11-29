package com.hana053.micropost.ui.pages.top;

import com.hana053.micropost.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent
public interface TopComponent {

    void inject(TopActivity activity);

    void inject(TopFragment fragment);
}
