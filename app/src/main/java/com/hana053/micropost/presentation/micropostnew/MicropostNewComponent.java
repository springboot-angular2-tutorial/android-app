package com.hana053.micropost.presentation.micropostnew;

import com.hana053.micropost.databinding.MicropostNewBinding;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.AppComponent;
import com.hana053.micropost.presentation.core.base.BaseFragment;

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

    // It's required. If it was removed, dagger will cause an error
    void inject(BaseFragment<MicropostNewViewModel, MicropostNewBinding> fragment);
}
