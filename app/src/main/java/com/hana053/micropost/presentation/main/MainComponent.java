package com.hana053.micropost.presentation.main;

import com.hana053.micropost.databinding.MainBinding;
import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.AppComponent;
import com.hana053.micropost.presentation.core.base.BaseFragment;

import java.util.List;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {AppComponent.class},
        modules = {
                ActivityModule.class,
                MainModule.class
        }
)
interface MainComponent {

    void inject(MainActivity activity);

    void inject(MainFragment mainFragment);

    // It's required. If it was removed, dagger will cause an error
    void inject(BaseFragment<List<Micropost>, MainBinding> fragment);
}
