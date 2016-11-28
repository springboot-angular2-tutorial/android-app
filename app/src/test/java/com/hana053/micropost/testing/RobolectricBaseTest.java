package com.hana053.micropost.testing;

import android.os.Build;

import com.hana053.micropost.BuildConfig;

import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(MyTestRunner.class)
@Config(constants = BuildConfig.class,
        packageName = "com.hana053.micropost",
        application = TestApplication.class,
        sdk = Build.VERSION_CODES.LOLLIPOP
)
public abstract class RobolectricBaseTest {

    private TestSchedulerProxy testScheduler = TestSchedulerProxy.get();

    protected TestApplication getTestApplication() {
        return ((TestApplication) RuntimeEnvironment.application);
    }

    protected void advance() {
        testScheduler.advance();
    }
}
