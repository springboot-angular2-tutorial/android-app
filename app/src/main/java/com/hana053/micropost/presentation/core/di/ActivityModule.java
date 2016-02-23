package com.hana053.micropost.presentation.core.di;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;
import rx.subscriptions.CompositeSubscription;

@Module
@AllArgsConstructor
public class ActivityModule {

    private final Activity activity;

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

}
