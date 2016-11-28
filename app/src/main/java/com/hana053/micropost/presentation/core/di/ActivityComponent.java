package com.hana053.micropost.presentation.core.di;

import com.hana053.micropost.presentation.login.LoginComponent;
import com.hana053.micropost.presentation.main.MainComponent;
import com.hana053.micropost.presentation.main.MainModule;
import com.hana053.micropost.presentation.micropostnew.MicropostNewComponent;
import com.hana053.micropost.presentation.micropostnew.MicropostNewModule;
import com.hana053.micropost.presentation.relateduserlist.followerlist.FollowerListComponent;
import com.hana053.micropost.presentation.relateduserlist.followerlist.FollowerListModule;
import com.hana053.micropost.presentation.relateduserlist.followinglist.FollowingListComponent;
import com.hana053.micropost.presentation.relateduserlist.followinglist.FollowingListModule;
import com.hana053.micropost.presentation.signup.SignupComponent;
import com.hana053.micropost.presentation.signup.SignupModule;
import com.hana053.micropost.presentation.top.TopComponent;
import com.hana053.micropost.presentation.usershow.UserShowComponent;
import com.hana053.micropost.presentation.usershow.UserShowModule;

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
