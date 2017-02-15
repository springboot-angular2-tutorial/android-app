package com.hana053.micropost

import android.os.StrictMode
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class Application : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeathOnNetwork().build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().detectActivityLeaks().penaltyLog().build())

        Timber.plant(Timber.DebugTree())
    }

}
