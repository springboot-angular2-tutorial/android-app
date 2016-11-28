package com.hana053.micropost.presentation.usershow;

import com.hana053.micropost.interactors.UserMicropostInteractor;
import com.hana053.micropost.presentation.core.components.micropostlist.PostListAdapter;
import com.hana053.micropost.presentation.core.di.ActivityScope;

import dagger.Module;
import dagger.Provides;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@SuppressWarnings("WeakerAccess")
@Module
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserShowModule {

    @Provides
    @ActivityScope
    public PostListAdapter providePostListAdapter() {
        return new PostListAdapter();
    }

    @Provides
    @ActivityScope
    public UserShowService provideUserShowService(UserMicropostInteractor userMicropostInteractor,
                                                  PostListAdapter postAdapter) {
        return new UserShowServiceImpl(userMicropostInteractor, postAdapter);

    }

}
