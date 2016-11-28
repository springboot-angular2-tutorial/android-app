package com.hana053.micropost.presentation.relateduserlist.followerlist;

import com.hana053.micropost.interactors.RelatedUserListInteractor;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.services.AuthTokenService;
import com.hana053.micropost.presentation.relateduserlist.RelatedUserListAdapter;
import com.hana053.micropost.presentation.relateduserlist.RelatedUserListService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("WeakerAccess")
@Module
public class FollowerListModule {

    @Provides
    @ActivityScope
    public RelatedUserListAdapter provideUserListAdapter(AuthTokenService authTokenService) {
        return new RelatedUserListAdapter(authTokenService);
    }

    @Provides
    @ActivityScope
    public RelatedUserListService provideRelatedUserListService(@Named("followers") RelatedUserListInteractor interactor, RelatedUserListAdapter userListAdapter) {
        return new RelatedUserListService(interactor, userListAdapter);
    }

}
