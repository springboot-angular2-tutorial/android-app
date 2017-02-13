package com.hana053.micropost.testing;

import android.os.StrictMode;

import com.hana053.micropost.DaggerAppComponent;
import com.hana053.micropost.interactors.InteractorModule;
import com.hana053.micropost.BaseApplication;
import com.hana053.micropost.AppComponent;
import com.hana053.micropost.services.ServiceModule;
import com.hana053.micropost.system.SystemServicesModule;

import timber.log.Timber;

public class TestApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeathOnNetwork().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().detectActivityLeaks().penaltyLog().build());

        Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .systemServicesModule(new SystemServicesModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }

    public void setComponent(AppComponent component) {
        this.component = component;
    }

}
