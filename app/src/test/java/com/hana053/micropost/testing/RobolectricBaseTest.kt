package com.hana053.micropost.testing

import android.os.Build
import com.hana053.micropost.Application
import com.hana053.micropost.BaseApplication
import com.hana053.micropost.BuildConfig
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    constants = BuildConfig::class,
    packageName = "com.hana053.micropost",
    application = Application::class,
    sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP)
)
abstract class RobolectricBaseTest : InjectableTest {

    override val app = RuntimeEnvironment.application as BaseApplication

    private val testScheduler = TestSchedulerProxy.get()

    fun advance() {
        testScheduler.advance()
    }

}
