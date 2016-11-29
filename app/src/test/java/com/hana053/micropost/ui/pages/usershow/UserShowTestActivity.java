package com.hana053.micropost.ui.pages.usershow;

import android.support.v4.app.FragmentActivity;

import com.hana053.micropost.BaseApplication;
import com.hana053.micropost.di.HasComponent;
import com.hana053.micropost.ui.ActivityModule;

public class UserShowTestActivity extends FragmentActivity implements HasComponent<UserShowComponent> {
    @Override
    public UserShowComponent getComponent() {
        return BaseApplication.component(this)
                .activityComponent(new ActivityModule(this))
                .userShowComponent(new UserShowModule());
    }
}
