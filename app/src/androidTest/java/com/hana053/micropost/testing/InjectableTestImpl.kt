package com.hana053.micropost.testing

import android.support.test.InstrumentationRegistry
import com.hana053.micropost.BaseApplication


class InjectableTestImpl : InjectableTest {
    override val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as BaseApplication
}