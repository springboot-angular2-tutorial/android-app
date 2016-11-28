package com.hana053.micropost.presentation.micropostnew;

import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = MicropostNewModule.class)
@SuppressWarnings("WeakerAccess")
public interface MicropostNewComponent {

    void inject(MicropostNewActivity micropostNewActivity);

    void inject(MicropostNewFragment micropostNewFragment);

}
