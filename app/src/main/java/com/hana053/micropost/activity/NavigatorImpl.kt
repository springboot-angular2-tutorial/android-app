package com.hana053.micropost.activity

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
import java.lang.ref.WeakReference

class NavigatorImpl(activity: Activity) : Navigator {

    private val activity = WeakReference<Activity>(activity)

    override fun navigateToTop() {
        val intent = Intent(activity.get(), TopActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.get()?.startActivity(intent)
    }

    override fun navigateToMain() {
        val intent = Intent(activity.get(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.get()?.startActivity(intent)
    }

    override fun navigateToLogin() {
        val intent = Intent(activity.get(), LoginActivity::class.java)
        activity.get()?.startActivity(intent)
    }

    override fun navigateToSignup() {
        val intent = Intent(activity.get(), SignupActivity::class.java)
        activity.get()?.startActivity(intent)
    }

    override fun navigateToUserShow(userId: Long) {
        val intent = Intent(activity.get(), UserShowActivity::class.java)
        intent.putExtra(UserShowActivity.KEY_USER_ID, userId)
        activity.get()?.startActivity(intent)
    }

    override fun navigateToFollowerList(userId: Long) {
        val intent = Intent(activity.get(), RelatedUserListActivity::class.java)
        intent.putExtra(RelatedUserListActivity.KEY_USER_ID, userId)
        intent.putExtra(RelatedUserListActivity.KEY_LIST_TYPE, FOLLOWER)
        activity.get()?.startActivity(intent)
    }

    override fun navigateToFollowingList(userId: Long) {
        val intent = Intent(activity.get(), RelatedUserListActivity::class.java)
        intent.putExtra(RelatedUserListActivity.KEY_USER_ID, userId)
        intent.putExtra(RelatedUserListActivity.KEY_LIST_TYPE, FOLLOWING)
        activity.get()?.startActivity(intent)
    }

    override fun navigateToMicropostNew() {
        val intent = Intent(activity.get(), MicropostNewActivity::class.java)
        activity.get()?.startActivityForResult(intent, REQUEST_POST)
        activity.get()?.overridePendingTransition(R.anim.slide_in_up, 0)
    }
}
