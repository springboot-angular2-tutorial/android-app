package com.hana053.micropost.ui;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
@SuppressWarnings("WeakerAccess")
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    public CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

    @Provides
    @ActivityScope
    public Navigator provideNavigator() {
        return new NavigatorImpl(activity);
    }


}
