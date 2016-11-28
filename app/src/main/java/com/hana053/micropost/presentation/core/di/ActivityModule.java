package com.hana053.micropost.presentation.core.di;

import android.app.Activity;

import com.hana053.micropost.interactors.RelationshipInteractor;
import com.hana053.micropost.presentation.core.components.followbtn.FollowBtnService;
import com.hana053.micropost.presentation.core.components.followbtn.FollowBtnServiceImpl;
import com.hana053.micropost.presentation.core.services.HttpErrorHandler;
import com.hana053.micropost.presentation.core.services.Navigator;
import com.hana053.micropost.presentation.core.services.NavigatorImpl;
import com.hana053.micropost.presentation.core.services.ProgressBarHandler;
import com.hana053.micropost.presentation.core.services.ProgressBarHandlerImpl;

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
    public ProgressBarHandler provideProgressBarHandler() {
        return new ProgressBarHandlerImpl(activity);
    }

    @Provides
    @ActivityScope
    public Navigator provideNavigator() {
        return new NavigatorImpl(activity);
    }

    @Provides
    @ActivityScope
    public FollowBtnService provideFollowBtnService(RelationshipInteractor relationshipInteractor,
                                                    HttpErrorHandler httpErrorHandler,
                                                    CompositeSubscription compositeSubscription) {
        return new FollowBtnServiceImpl(relationshipInteractor, httpErrorHandler, compositeSubscription);
    }

}
