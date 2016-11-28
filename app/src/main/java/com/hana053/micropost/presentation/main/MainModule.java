package com.hana053.micropost.presentation.main;

import com.hana053.micropost.interactors.FeedInteractor;
import com.hana053.micropost.presentation.core.components.micropostlist.PostListAdapter;
import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Module;
import dagger.Provides;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Module
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@SuppressWarnings("WeakerAccess")
public class MainModule {

    private final MainCtrl mainCtrl;

    @Provides
    @ActivityScope
    public MainCtrl provideCtrl() {
        return mainCtrl;
    }

    @Provides
    @ActivityScope
    public PostListAdapter providePostListAdapter() {
        return new PostListAdapter();
    }

    @Provides
    @ActivityScope
    public MainService provideMainService(FeedInteractor feedInteractor, PostListAdapter postListAdapter) {
        return new MainServiceImpl(feedInteractor, postListAdapter);
    }
}
