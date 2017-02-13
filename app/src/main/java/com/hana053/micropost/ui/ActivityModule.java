package com.hana053.micropost.ui;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;
import rx.subscriptions.CompositeSubscription;

@Module
@AllArgsConstructor
@SuppressWarnings("WeakerAccess")
public class ActivityModule {

    private final Activity activity;

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
