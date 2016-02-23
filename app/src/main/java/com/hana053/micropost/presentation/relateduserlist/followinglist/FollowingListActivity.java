package com.hana053.micropost.presentation.relateduserlist.followinglist;

import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ExplicitHasComponent;
import com.hana053.micropost.presentation.relateduserlist.RelatedUserListBaseActivity;

import lombok.Getter;

public class FollowingListActivity extends RelatedUserListBaseActivity implements ExplicitHasComponent<FollowingListComponent> {

    @Getter
    private FollowingListComponent component;

    @Override
    protected void prepareComponent() {
        component = DaggerFollowingListComponent.builder()
                .appComponent(BaseApplication.component(this))
                .activityModule(new ActivityModule(this))
                .followingListModule(new FollowingListModule())
                .build();
        component.inject(this);
    }

    @Override
    public Class<FollowingListComponent> getComponentType() {
        return FollowingListComponent.class;
    }
}
