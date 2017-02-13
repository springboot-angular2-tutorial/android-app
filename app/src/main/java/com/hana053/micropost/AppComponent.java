package com.hana053.micropost;

import com.hana053.micropost.services.ServiceModule;
import com.hana053.micropost.system.SystemServicesModule;
import com.hana053.micropost.ui.ActivityModule;
import com.hana053.micropost.ui.pages.top.TopComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        SystemServicesModule.class,
        ServiceModule.class,
})
public interface AppComponent {

    TopComponent topComponent(ActivityModule activityModule);

}
