package com.hana053.micropost.presentation.relateduserlist.followerlist;

import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.relateduserlist.RelatedUserListFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = FollowerListModule.class)
public interface FollowerListComponent {

    void inject(FollowerListActivity activity);

    void inject(RelatedUserListFragment fragment);

}
