package com.hana053.micropost.ui;

import com.hana053.micropost.ui.pages.login.LoginComponent;
import com.hana053.micropost.ui.pages.main.MainComponent;
import com.hana053.micropost.ui.pages.main.MainModule;
import com.hana053.micropost.ui.pages.micropostnew.MicropostNewComponent;
import com.hana053.micropost.ui.pages.micropostnew.MicropostNewModule;
import com.hana053.micropost.ui.pages.relateduserlist.followerlist.FollowerListComponent;
import com.hana053.micropost.ui.pages.relateduserlist.followerlist.FollowerListModule;
import com.hana053.micropost.ui.pages.relateduserlist.followinglist.FollowingListComponent;
import com.hana053.micropost.ui.pages.relateduserlist.followinglist.FollowingListModule;
import com.hana053.micropost.ui.pages.signup.SignupComponent;
import com.hana053.micropost.ui.pages.signup.SignupModule;
import com.hana053.micropost.ui.pages.top.TopComponent;
import com.hana053.micropost.ui.pages.usershow.UserShowComponent;
import com.hana053.micropost.ui.pages.usershow.UserShowModule;

import dagger.Subcomponent;

@SuppressWarnings("WeakerAccess")
@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    MainComponent mainComponent(MainModule mainModule);

    MicropostNewComponent micropostNewComponent(MicropostNewModule micropostNewModule);

    LoginComponent loginComponent();

    FollowerListComponent followerListComponent(FollowerListModule followerListModule);

    FollowingListComponent followingListComponent(FollowingListModule followingListModule);

    SignupComponent signupComponent(SignupModule signupModule);

    TopComponent topComponent();

    UserShowComponent userShowComponent(UserShowModule userShowModule);

}
