package com.hana053.micropost;

import com.hana053.micropost.interactors.InteractorModule;
import com.hana053.micropost.ui.ActivityComponent;
import com.hana053.micropost.ui.ActivityModule;
import com.hana053.micropost.services.ServiceModule;
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
