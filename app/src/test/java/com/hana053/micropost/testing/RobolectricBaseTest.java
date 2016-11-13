package com.hana053.micropost.testing;

import android.os.Build;

import com.hana053.micropost.BuildConfig;
import com.hana053.micropost.presentation.core.di.AppComponent;
import com.hana053.micropost.testing.shadows.ShadowAuthTokenServiceFactory;
import com.hana053.micropost.testing.shadows.ShadowFeedInteractorFactory;
import com.hana053.micropost.testing.shadows.ShadowLoginInteractorFactory;
import com.hana053.micropost.testing.shadows.ShadowMicropostInteractorFactory;
import com.hana053.micropost.testing.shadows.ShadowPicasso;
import com.hana053.micropost.testing.shadows.ShadowUserInteractorFactory;
import com.hana053.micropost.testing.shadows.ShadowUserMicropostInteractorFactory;

import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(MyTestRunner.class)
@Config(constants = BuildConfig.class,
        packageName = "com.hana053.micropost",
        application = TestApplication.class,
        sdk = Build.VERSION_CODES.LOLLIPOP,
        shadows = {
                ShadowPicasso.class,
                ShadowFeedInteractorFactory.class,
                ShadowMicropostInteractorFactory.class,
                ShadowLoginInteractorFactory.class,
                ShadowUserInteractorFactory.class,
                ShadowUserMicropostInteractorFactory.class,
                ShadowAuthTokenServiceFactory.class,
        }
)
public abstract class RobolectricBaseTest {

    private TestSchedulerProxy testScheduler = TestSchedulerProxy.get();

    protected TestApplication getTestApplication() {
        return ((TestApplication) RuntimeEnvironment.application);
    }

    protected AppComponent getAppComponent() {
        return getTestApplication().getComponent();
    }

    protected void advance() {
        testScheduler.advance();
    }
}
