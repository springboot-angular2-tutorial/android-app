package com.hana053.micropost.presentation.micropostnew;

import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Module;
import dagger.Provides;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Module
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MicropostNewModule {

    private final MicropostNewCtrl micropostNewCtrl;

    @Provides
    @ActivityScope
    MicropostNewCtrl provideCtrl() {
        return micropostNewCtrl;
    }

}
