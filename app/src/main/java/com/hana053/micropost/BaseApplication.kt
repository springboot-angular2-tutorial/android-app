package com.hana053.micropost

import android.app.Application
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.activity.NavigatorImpl
import com.hana053.micropost.interactors.interactorModule
import com.hana053.micropost.services.serviceModule
import com.hana053.micropost.system.systemServiceModule

abstract class BaseApplication : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
    }

    //    override val kodein: Kodein by Kodein.lazy {
//        import(systemServiceModule())
//        import(serviceModule())
//        import(interactorModule())
//
//        bind<Navigator>() with autoScopedSingleton(androidActivityScope) {
//            NavigatorImpl(instance())
//        }
//    }
    protected var _kodein = Kodein {
        import(systemServiceModule())
        import(serviceModule())
        import(interactorModule())

        bind<Navigator>() with autoScopedSingleton(androidActivityScope) {
            NavigatorImpl(instance())
        }
    }

    override val kodein: Kodein
        get() = _kodein

}
