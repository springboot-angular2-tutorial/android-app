package com.hana053.micropost;

import com.hana053.micropost.interactors.InteractorModule;
import com.hana053.micropost.services.AuthTokenService;
import com.hana053.micropost.services.ServiceModule;
import com.hana053.micropost.system.SystemServicesModule;
import com.hana053.micropost.ui.ActivityModule;
import com.hana053.micropost.ui.pages.top.TopComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        SystemServicesModule.class,
        InteractorModule.class,
        ServiceModule.class,
})
public interface AppComponent {

    TopComponent topComponent(ActivityModule activityModule);

    AuthTokenService authTokenService();

}
