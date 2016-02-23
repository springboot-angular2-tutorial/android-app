package com.hana053.micropost.presentation.usershow;

import com.hana053.micropost.presentation.core.components.micropostlist.PostListAdapter;
import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Module;
import dagger.Provides;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Module
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserShowModule {

    @Provides
    @ActivityScope
    PostListAdapter providePostListAdapter() {
        return new PostListAdapter();
    }

}
