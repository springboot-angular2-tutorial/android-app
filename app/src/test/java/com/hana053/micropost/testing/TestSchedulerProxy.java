package com.hana053.micropost.testing;

import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.TestScheduler;

/**
 * paste from https://gist.github.com/pakerfeldt/db297764918e7d464884#file-testschedulerproxy-java
 * http://alexismas.com/blog/2015/05/20/unit-testing-rxjava/#comment-2260797067
 */
class TestSchedulerProxy {

    private static final TestScheduler SCHEDULER = new TestScheduler();
    private static final TestSchedulerProxy INSTANCE = new TestSchedulerProxy();

    static {
        try {
            RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
                @Override
                public Scheduler getIOScheduler() {
                    return SCHEDULER;
                }

                @Override
                public Scheduler getComputationScheduler() {
                    return SCHEDULER;
                }

                @Override
                public Scheduler getNewThreadScheduler() {
                    return SCHEDULER;
                }
            });
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Schedulers class already initialized. " +
                    "Ensure you always use the TestSchedulerProxy in unit tests.");
        }
    }

    public static TestSchedulerProxy get() {
        return INSTANCE;
    }

    public void advanceBy(long delayTime, TimeUnit unit) {
        SCHEDULER.advanceTimeBy(delayTime, unit);
    }

    public void advance() {
       advanceBy(1, TimeUnit.SECONDS);
    }

}

