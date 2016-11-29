package com.hana053.micropost.ui.pages.usershow;

import com.hana053.micropost.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = UserShowModule.class)
public interface UserShowComponent {

    void inject(UserShowActivity activity);

    void inject(UserShowPostListFragment fragment);

    void inject(UserShowFragment fragment);

}
