package com.hana053.micropost.testing

import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnitRunner
import com.linkedin.android.testbutler.TestButler


class AppTestRunner : AndroidJUnitRunner() {

    override fun onStart() {
        TestButler.setup(InstrumentationRegistry.getTargetContext())
        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle) {
        TestButler.teardown(InstrumentationRegistry.getTargetContext())
        super.finish(resultCode, results)
    }

}