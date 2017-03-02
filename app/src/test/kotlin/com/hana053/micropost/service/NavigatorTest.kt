package com.hana053.micropost.service

import android.app.Activity
import android.content.Intent
import com.hana053.micropost.pages.login.LoginActivity
import com.hana053.micropost.pages.main.MainActivity
import com.hana053.micropost.pages.main.MainActivity.Companion.REQUEST_POST
import com.hana053.micropost.pages.micropostnew.MicropostNewActivity
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity
import com.hana053.micropost.pages.signup.SignupActivity
import com.hana053.micropost.pages.usershow.UserShowActivity
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.Shadows


class NavigatorTest : RobolectricBaseTest() {

    lateinit private var activity: TestActivity
    lateinit private var navigator: Navigator

    @Before
    fun setup() {
        activity = Robolectric.setupActivity(TestActivity::class.java)
        navigator = NavigatorImpl(activity)
    }

    @Test
    fun shouldNavigateToUserShow() {
        val user = TestUser

        navigator.navigateToUserShow(user.id)

        val shadow = Shadows.shadowOf(activity)
        val intent = shadow.nextStartedActivity

        assertThat(intent.component.className).isEqualTo(UserShowActivity::class.java.name)
        assertThat(intent.extras.getLong(UserShowActivity.KEY_USER_ID)).isEqualTo(1)
    }

    @Test
    fun shouldNavigateToMain() {
        navigator.navigateToMain()

        val shadow = Shadows.shadowOf(activity)
        val intent = shadow.nextStartedActivity

        assertThat(intent.component.className).isEqualTo(MainActivity::class.java.name)
    }

    @Test
    fun shouldNavigateToSignup() {
        navigator.navigateToSignup()

        val shadow = Shadows.shadowOf(activity)
        val intent = shadow.nextStartedActivity

        assertThat(intent.component.className).isEqualTo(SignupActivity::class.java.name)
    }

    @Test
    fun shouldNavigateToLogin() {
        navigator.navigateToLogin()

        val shadow = Shadows.shadowOf(activity)
        val intent = shadow.nextStartedActivity

        assertThat(intent.component.className).isEqualTo(LoginActivity::class.java.name)
    }

    @Test
    fun shouldNavigateToFollowings() {
        navigator.navigateToFollowingList(1)

        val shadow = Shadows.shadowOf(activity)
        val intent = shadow.nextStartedActivity

        assertThat(intent.component.className).isEqualTo(RelatedUserListActivity::class.java.name)
        assertThat(intent.extras.getLong(RelatedUserListActivity.KEY_USER_ID)).isEqualTo(1)
        assertThat(intent.extras.getSerializable(RelatedUserListActivity.KEY_LIST_TYPE) as RelatedUserListActivity.ListType)
            .isEqualTo(RelatedUserListActivity.ListType.FOLLOWING)
    }

    @Test
    fun shouldNavigateToFollowers() {
        navigator.navigateToFollowerList(1)

        val shadow = Shadows.shadowOf(activity)
        val intent = shadow.nextStartedActivity

        assertThat(intent.component.className).isEqualTo(RelatedUserListActivity::class.java.name)
        assertThat(intent.extras.getLong(RelatedUserListActivity.KEY_USER_ID)).isEqualTo(1)
        assertThat(intent.extras.getSerializable(RelatedUserListActivity.KEY_LIST_TYPE) as RelatedUserListActivity.ListType)
            .isEqualTo(RelatedUserListActivity.ListType.FOLLOWER)
    }

    @Test
    fun shouldNavigateToMicropostNew() {
        navigator.navigateToMicropostNew()

        val shadow = Shadows.shadowOf(activity)
        val intent = shadow.nextStartedActivity

        assertThat(intent.component.className).isEqualTo(MicropostNewActivity::class.java.name)

        shadow.receiveResult(
            Intent(activity, MicropostNewActivity::class.java),
            Activity.RESULT_OK,
            Intent()
        )
        assertThat(activity.postReceived).isTrue()
    }

    class TestActivity : Activity() {
        var postReceived = false

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_POST) postReceived = true
        }
    }

}