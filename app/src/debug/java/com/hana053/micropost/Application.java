package com.hana053.micropost;

import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class Application extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeathOnNetwork().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().detectActivityLeaks().penaltyLog().build());

        Timber.plant(new Timber.DebugTree());
    }

}
