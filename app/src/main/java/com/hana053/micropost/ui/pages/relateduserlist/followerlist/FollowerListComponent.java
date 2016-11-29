package com.hana053.micropost.ui.pages.relateduserlist.followerlist;

import com.hana053.micropost.ui.ActivityScope;
import com.hana053.micropost.ui.pages.relateduserlist.RelatedUserListFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = FollowerListModule.class)
public interface FollowerListComponent {

    void inject(FollowerListActivity activity);

    void inject(RelatedUserListFragment fragment);

}
