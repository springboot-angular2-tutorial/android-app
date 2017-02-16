package com.hana053.micropost.testing

import com.github.salomonbrys.kodein.Kodein

interface InjectableTest {

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