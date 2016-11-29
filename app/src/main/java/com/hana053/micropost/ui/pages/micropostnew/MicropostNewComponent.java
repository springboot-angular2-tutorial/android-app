package com.hana053.micropost.ui.pages.micropostnew;

import com.hana053.micropost.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = MicropostNewModule.class)
@SuppressWarnings("WeakerAccess")
public interface MicropostNewComponent {

    void inject(MicropostNewActivity micropostNewActivity);

    void inject(MicropostNewFragment micropostNewFragment);

}
