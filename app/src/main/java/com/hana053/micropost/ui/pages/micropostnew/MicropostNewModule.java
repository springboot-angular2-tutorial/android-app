package com.hana053.micropost.ui.pages.micropostnew;

import com.hana053.micropost.interactors.MicropostInteractor;
import com.hana053.micropost.ui.ActivityScope;

import dagger.Module;
import dagger.Provides;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Module
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@SuppressWarnings("WeakerAccess")
public class MicropostNewModule {

    private final MicropostNewCtrl micropostNewCtrl;

    @Provides
    @ActivityScope
    public MicropostNewCtrl provideCtrl() {
        return micropostNewCtrl;
    }

    @Provides
    @ActivityScope
    public MicropostNewService provideMicropostNewService(MicropostInteractor micropostInteractor) {
        return new MicropostNewServiceImpl(micropostInteractor);
    }

}
