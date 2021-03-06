package com.hana053.micropost.service

import android.app.Activity
import android.content.Intent
import com.hana053.micropost.R
import com.hana053.micropost.pages.login.LoginActivity
import com.hana053.micropost.pages.main.MainActivity
import com.hana053.micropost.pages.main.MainActivity.Companion.REQUEST_POST
import com.hana053.micropost.pages.micropostnew.MicropostNewActivity
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity.ListType.FOLLOWER
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity.ListType.FOLLOWING
import com.hana053.micropost.pages.signup.SignupActivity
import com.hana053.micropost.pages.top.TopActivity
import com.hana053.micropost.pages.usershow.UserShowActivity

class NavigatorImpl(private val activity: Activity) : Navigator {

    override fun navigateToTop() {
        val intent = Intent(activity, TopActivity::class.java)
        activity.finishAffinity()
        activity.startActivity(intent)
    }

    override fun navigateToMain() {
        val intent = Intent(activity, MainActivity::class.java)
        activity.finishAffinity()
        activity.startActivity(intent)
    }

    override fun navigateToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
    }

    override fun navigateToSignup() {
        val intent = Intent(activity, SignupActivity::class.java)
        activity.startActivity(intent)
    }

    override fun navigateToUserShow(userId: Long) {
        val intent = Intent(activity, UserShowActivity::class.java)
        intent.putExtra(UserShowActivity.KEY_USER_ID, userId)
        activity.startActivity(intent)
    }

    override fun navigateToFollowerList(userId: Long) {
        val intent = Intent(activity, RelatedUserListActivity::class.java)
        intent.putExtra(RelatedUserListActivity.KEY_USER_ID, userId)
        intent.putExtra(RelatedUserListActivity.KEY_LIST_TYPE, FOLLOWER)
        activity.startActivity(intent)
    }

    override fun navigateToFollowingList(userId: Long) {
        val intent = Intent(activity, RelatedUserListActivity::class.java)
        intent.putExtra(RelatedUserListActivity.KEY_USER_ID, userId)
        intent.putExtra(RelatedUserListActivity.KEY_LIST_TYPE, FOLLOWING)
        activity.startActivity(intent)
    }

    override fun navigateToMicropostNew() {
        val intent = Intent(activity, MicropostNewActivity::class.java)
        activity.startActivityForResult(intent, REQUEST_POST)
        activity.overridePendingTransition(R.anim.slide_in_up, 0)
    }
}
