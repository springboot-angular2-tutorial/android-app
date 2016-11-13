package com.hana053.micropost.presentation.relateduserlist.followinglist;

import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.AppComponent;
import com.hana053.micropost.presentation.relateduserlist.RelatedUserListFragment;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {AppComponent.class},
        modules = {
                ActivityModule.class,
                FollowingListModule.class,
        }
)
public interface FollowingListComponent {

    void inject(FollowingListActivity activity);

    void inject(RelatedUserListFragment fragment);

}

