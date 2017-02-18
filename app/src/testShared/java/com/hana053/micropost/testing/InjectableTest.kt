package com.hana053.micropost.testing

import android.app.Activity
import android.content.Context
import android.preference.PreferenceManager
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.BaseApplication
import com.hana053.micropost.appModule
import org.junit.Before


interface InjectableTest {

    val app: BaseApplication

    @Before
    fun resetSharedPreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun overrideAppBindings(init: Kodein.Builder.() -> Unit) {
        app.setKodein(Kodein {
            import(appModule(), allowOverride = true)
            import(blockedInteractorModule(), allowOverride = true)
            init()
        })
    }

    // For Robolectric use
    fun overrideAppBindingsWithContext(init: Kodein.Builder.() -> Unit = {}) {
        app.setKodein(Kodein {
            import(appModule(), allowOverride = true)
            import(blockedInteractorModule(), allowOverride = true)
            bind<Context>() with instance(app)
            init()
        })
    }

    // For Robolectric use
    fun overrideAppBindingsWithActivity(activity: Activity, init: Kodein.Builder.() -> Unit = {}) {
        app.setKodein(Kodein {
            import(appModule(), allowOverride = true)
            import(blockedInteractorModule(), allowOverride = true)
            bind<Context>() with instance(app)
            bind<Activity>() with instance(activity)
            init()
        })
    }

}