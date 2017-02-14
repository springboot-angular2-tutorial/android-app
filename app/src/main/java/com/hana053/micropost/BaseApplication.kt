package com.hana053.micropost

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.hana053.micropost.services.serviceModule
import com.hana053.micropost.system.systemServiceModule

abstract class BaseApplication : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
    }

    protected var _kodein = Kodein {
        import(systemServiceModule())
        import(serviceModule())
    }

    override val kodein: Kodein
        get() = _kodein

}
