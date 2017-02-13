package com.hana053.micropost.testing;


import com.hana053.micropost.AppComponent;
import com.hana053.micropost.interactors.InteractorModule;
import com.hana053.micropost.services.ServiceModule;
import com.hana053.micropost.system.SystemServicesModule;

import org.robolectric.RuntimeEnvironment;

import it.cosenonjaviste.daggermock.DaggerMockRule;

public class RobolectricDaggerMockRule extends DaggerMockRule<AppComponent> {

    public RobolectricDaggerMockRule() {
        super(AppComponent.class,
                new SystemServicesModule(RuntimeEnvironment.application),
                new InteractorModule(),
                new ServiceModule()
        );
        set(component -> {
                    component.authTokenService(); // TODO remove
                    ((TestApplication) RuntimeEnvironment.application).setComponent(component);
                }
        );
    }

}
