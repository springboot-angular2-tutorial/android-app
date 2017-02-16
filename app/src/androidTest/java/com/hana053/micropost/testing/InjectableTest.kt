package com.hana053.micropost.testing

import android.support.test.InstrumentationRegistry
import com.github.salomonbrys.kodein.Kodein
import com.hana053.micropost.Application

abstract class InjectableTest {

    val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application

    fun overrideAppBindings(init: Kodein.Builder.() -> Unit) {
        app.setKodein(Kodein {
            app.init(this)
            init()
        })
    }

    fun putOverridingModule(clazz: Class<*>, module: Kodein.Module, overrides: Boolean = true) {
        if (overrides) {
            // override existing module
            val currentModule = app.getOverridingModule(clazz)
            app.putOverridingModule(clazz, Kodein.Module {
                import(currentModule, allowOverride = true)
                import(module, allowOverride = true)
            })
        } else {
            // just replacing module
            app.putOverridingModule(clazz, module)
        }
    }

}