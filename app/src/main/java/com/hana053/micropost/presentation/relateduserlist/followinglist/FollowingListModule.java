package com.hana053.micropost.presentation.relateduserlist.followinglist;

import com.hana053.micropost.interactors.RelatedUserListInteractor;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.relateduserlist.RelatedUserListAdapter;
import com.hana053.micropost.presentation.relateduserlist.RelatedUserListService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class FollowingListModule {

    @Provides
    @ActivityScope
    public RelatedUserListAdapter provideUserListAdapter() {
        return new RelatedUserListAdapter();
    }

    @Provides
    @ActivityScope
    public RelatedUserListService provideRelatedUserListService(@Named("followings") RelatedUserListInteractor interactor, RelatedUserListAdapter userListAdapter) {
        return new RelatedUserListService(interactor, userListAdapter);
    }

}
