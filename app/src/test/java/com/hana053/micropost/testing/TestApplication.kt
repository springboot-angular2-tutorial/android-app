package com.hana053.micropost.testing

import android.os.StrictMode
import com.github.salomonbrys.kodein.Kodein
import com.hana053.micropost.BaseApplication
import timber.log.Timber

class TestApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeathOnNetwork().build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().detectActivityLeaks().penaltyLog().build())

        Timber.plant(Timber.DebugTree())
    }

    fun setKodein(kodein: Kodein) {
        _kodein = kodein
    }


}
