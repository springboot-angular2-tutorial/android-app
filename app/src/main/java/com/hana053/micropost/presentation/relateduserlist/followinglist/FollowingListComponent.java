package com.hana053.micropost.presentation.relateduserlist.followinglist;

import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.relateduserlist.RelatedUserListAdapter;
import com.hana053.micropost.presentation.relateduserlist.RelatedUserListFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = FollowingListModule.class)
public interface FollowingListComponent {

    void inject(FollowingListActivity activity);

    void inject(RelatedUserListFragment fragment);

    RelatedUserListAdapter relatedUserListAdapter();
}

