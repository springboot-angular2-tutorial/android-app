package com.hana053.micropost.presentation.main;

import com.hana053.micropost.presentation.core.components.micropostlist.PostListAdapter;
import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Module;
import dagger.Provides;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Module
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MainModule {

    private final MainCtrl mainCtrl;

    @Provides
    @ActivityScope
    MainCtrl provideCtrl() {
        return mainCtrl;
    }

    @Provides
    @ActivityScope
    PostListAdapter providePostListAdapter() {
        return new PostListAdapter();
    }

}
