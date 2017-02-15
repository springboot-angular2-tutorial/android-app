package com.hana053.micropost.testing

import android.support.test.InstrumentationRegistry
import com.github.salomonbrys.kodein.Kodein
import com.hana053.micropost.Application


interface InjectableTest {

    fun overrideAppBindings(init: Kodein.Builder.() -> Unit) {
        val app = app()
        val kodein = Kodein {
            extend(app.kodein)
            init()
        }
        app.setKodein(kodein)
    }

    fun putOverridingModule(clazz: Class<*>, module: Kodein.Module, overrides: Boolean = true) {
        val app = app()

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

    private fun app(): Application {
        return InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application
    }
}