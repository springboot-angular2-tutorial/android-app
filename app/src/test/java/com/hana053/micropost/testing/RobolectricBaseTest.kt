package com.hana053.micropost.testing

import android.os.Build
import com.github.salomonbrys.kodein.Kodein

import com.hana053.micropost.BuildConfig

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    constants = BuildConfig::class,
    packageName = "com.hana053.micropost",
    application = TestApplication::class,
    sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP)
)
abstract class RobolectricBaseTest {

    private val testScheduler = TestSchedulerProxy.get()

    fun advance() {
        testScheduler.advance()
    }

    fun overrideAppBindings(init: Kodein.Builder.() -> Unit) {
        val application = RuntimeEnvironment.application as TestApplication
        val kodein = Kodein {
            extend(application.kodein)
            init()
        }
        application.setKodein(kodein)
    }

    fun putOverridingModule(clazz: Class<*>, module: Kodein.Module, overrides: Boolean = true) {
        val app = RuntimeEnvironment.application as TestApplication

        if (overrides) {
            // override existing module
            val currentModule = app.getOverridingModule(clazz)
            app.putOverridingModule(clazz, Kodein.Module {
                import(currentModule)
                import(module)
            })
        } else {
            // just replacing module
            app.putOverridingModule(clazz, module)
        }
    }
}
