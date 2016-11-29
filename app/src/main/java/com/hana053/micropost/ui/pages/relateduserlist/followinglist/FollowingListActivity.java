package com.hana053.micropost.ui.pages.relateduserlist.followinglist;

import com.hana053.micropost.BaseApplication;
import com.hana053.micropost.di.ExplicitHasComponent;
import com.hana053.micropost.ui.ActivityModule;
import com.hana053.micropost.ui.pages.relateduserlist.RelatedUserListBaseActivity;

import lombok.Getter;

public class FollowingListActivity extends RelatedUserListBaseActivity implements ExplicitHasComponent<FollowingListComponent> {

    @Getter
    private FollowingListComponent component;

    @Override
    protected void prepareComponent() {
        component = BaseApplication.component(this)
                .activityComponent(new ActivityModule(this))
                .followingListComponent(new FollowingListModule());
        component.inject(this);
    }

    @Override
    public Class<FollowingListComponent> getComponentType() {
        return FollowingListComponent.class;
    }
}
