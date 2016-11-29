package com.hana053.micropost.ui.pages.relateduserlist.followinglist;

import com.hana053.micropost.ui.ActivityScope;
import com.hana053.micropost.ui.pages.relateduserlist.RelatedUserListFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = FollowingListModule.class)
public interface FollowingListComponent {

    void inject(FollowingListActivity activity);

    void inject(RelatedUserListFragment fragment);
}

