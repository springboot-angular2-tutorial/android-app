package com.hana053.micropost.presentation.core.services;

import android.app.Activity;
import android.content.Intent;

import com.hana053.micropost.domain.User;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.login.LoginActivity;
import com.hana053.micropost.presentation.main.MainActivity;
import com.hana053.micropost.presentation.relateduserlist.followerlist.FollowerListActivity;
import com.hana053.micropost.presentation.relateduserlist.followinglist.FollowingListActivity;
import com.hana053.micropost.presentation.signup.SignupActivity;
import com.hana053.micropost.presentation.usershow.UserShowActivity;

import org.parceler.Parcels;

import javax.inject.Inject;

@ActivityScope
public class Navigator {

    private final Activity activity;

    @Inject
    Navigator(Activity activity) {
        this.activity = activity;
    }

    public void navigateToUserShow(User user) {
        final Intent intent = new Intent(activity, UserShowActivity.class);
        intent.putExtra(UserShowActivity.KEY_USER, Parcels.wrap(user));
        activity.startActivity(intent);
    }

    public void navigateToMain() {
        final Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    public void navigateToSignup() {
        final Intent intent = new Intent(activity, SignupActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToLogin() {
        final Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToFollowings(long userId) {
        final Intent intent = new Intent(activity, FollowingListActivity.class);
        intent.putExtra(FollowingListActivity.KEY_USER_ID, userId);
        activity.startActivity(intent);
    }

    public void navigateToFollowers(long userId) {
        final Intent intent = new Intent(activity, FollowerListActivity.class);
        intent.putExtra(FollowerListActivity.KEY_USER_ID, userId);
        activity.startActivity(intent);
    }
}
