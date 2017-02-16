package com.hana053.micropost.testing

import android.support.test.InstrumentationRegistry
import com.github.salomonbrys.kodein.Kodein
import com.hana053.micropost.Application
import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.domain.User
import com.hana053.micropost.domain.UserStats

abstract class InjectableTest {

    val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application

    val TestUserStats = UserStats(1, 2, 3)
    val TestUser = User(1, "John Doe", "test@test.com", "hash", false, TestUserStats)
    val TestMicropost = Micropost(1, "content", 0, TestUser)

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