package com.hana053.micropost.presentation.core.di;

import com.hana053.micropost.interactors.InteractorModule;
import com.hana053.micropost.presentation.core.services.ServiceModule;
import com.hana053.micropost.system.SystemServicesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        SystemServicesModule.class,
        InteractorModule.class,
        ServiceModule.class,
})
public interface AppComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

}
