package com.hana053.micropost.testing

import rx.plugins.RxJavaHooks
import rx.schedulers.TestScheduler
import java.util.concurrent.TimeUnit

internal class TestSchedulerProxy {

    fun advanceBy(delayTime: Long, unit: TimeUnit) {
        SCHEDULER.advanceTimeBy(delayTime, unit)
    }

    fun advance() {
        advanceBy(1, TimeUnit.SECONDS)
    }

    companion object {
        private val SCHEDULER = TestScheduler()
        private val INSTANCE = TestSchedulerProxy()

        init {
            try {
                RxJavaHooks.setOnIOScheduler { SCHEDULER }
                RxJavaHooks.setOnComputationScheduler { SCHEDULER }
                RxJavaHooks.setOnNewThreadScheduler { SCHEDULER }
            } catch (e: IllegalStateException) {
                throw IllegalStateException("Schedulers class already initialized. " + "Ensure you always use the TestSchedulerProxy in unit tests.")
            }
        }

        fun get(): TestSchedulerProxy {
            return INSTANCE
        }
    }

}

