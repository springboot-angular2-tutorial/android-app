package com.hana053.micropost.presentation.relateduserlist.followerlist;

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
                FollowerListModule.class,
        }
)
public interface FollowerListComponent {

    void inject(FollowerListActivity activity);

    void inject(RelatedUserListFragment fragment);

}
