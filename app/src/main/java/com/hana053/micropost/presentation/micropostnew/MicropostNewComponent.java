package com.hana053.micropost.presentation.micropostnew;

import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.AppComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {AppComponent.class},
        modules = {
                ActivityModule.class,
                MicropostNewModule.class
        }
)
interface MicropostNewComponent {

    void inject(MicropostNewActivity micropostNewActivity);

    void inject(MicropostNewFragment micropostNewFragment);

}
