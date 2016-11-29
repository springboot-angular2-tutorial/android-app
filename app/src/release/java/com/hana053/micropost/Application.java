package com.hana053.micropost;

import com.hana053.micropost.system.SystemServicesModule;

import timber.log.Timber;

public class Application extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.Tree() {
            @Override
            protected void log(int priority, String tag, String message, Throwable t) {
            }
        });
    }

    @Override
    protected AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .systemServicesModule(new SystemServicesModule(this))
                .build();
    }

}
