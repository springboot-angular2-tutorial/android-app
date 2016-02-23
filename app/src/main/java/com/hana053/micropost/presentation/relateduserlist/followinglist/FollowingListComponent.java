package com.hana053.micropost.presentation.relateduserlist.followinglist;

import com.hana053.micropost.databinding.RelatedUserListBinding;
import com.hana053.micropost.domain.User;
import com.hana053.micropost.presentation.core.base.BaseFragment;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.AppComponent;
import com.hana053.micropost.presentation.relateduserlist.RelatedUserListFragment;

import java.util.List;

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

    // It's required. If it was removed, dagger will cause an error
    void inject(BaseFragment<List<User>, RelatedUserListBinding> fragment);

}

