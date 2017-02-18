package com.hana053.micropost.pages.relateduserlist

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.domain.RelatedUser
import com.hana053.micropost.interactors.RelatedUserListInteractor
import com.hana053.micropost.interactors.RelationshipInteractor
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity.ListType.FOLLOWER
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity.ListType.FOLLOWING
import com.hana053.micropost.testing.*
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable


@RunWith(AndroidJUnit4::class)
@LargeTest
class RelatedUserListActivityTest : InjectableTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule(RelatedUserListActivity::class.java, false, false)

    // ------------- FOLLOWER part -------------

    @Test
    fun shouldBeOpenedWhenAuthenticated() {
        overrideAppBindings {
            fakeAuthToken("secret")
            bind<RelatedUserListInteractor>(overrides = true) with instance(mock<RelatedUserListInteractor> {
                // just avoiding error
                on { listFollowers(userId = 1, maxId = null) } doReturn Observable.empty()
            })
        }
        launchActivity(FOLLOWER, 1)
        onView(allOf(
            isDescendantOfA(withResourceName("android:id/action_bar_container")),
            withText(R.string.Followers)
        ))
    }

    @Test
    fun shouldNotBeOpenedWhenNotAuthenticated() {
        val navigator = mock<Navigator>()
        overrideAppBindings {
            bind<Navigator>(overrides = true) with instance(navigator)
        }
        launchActivity(FOLLOWER, 1)
        verify(navigator).navigateToTop()
    }

    @Test
    fun shouldShowFollowers() {
        overrideAppBindings {
            fakeAuthToken("secret")
            bind<RelatedUserListInteractor>(overrides = true) with instance(mock<RelatedUserListInteractor> {
                on { listFollowers(1, null) } doReturn Observable.just(listOf(
                    TestRelatedUser.copy(relationshipId = 1, name = "John Doe")
                ))
                // just avoiding error
                on { listFollowers(userId = 1, maxId = 1) } doReturn Observable.empty()
            })
        }
        launchActivity(FOLLOWER, 1)

        onView(withId(R.id.userRecyclerView))
            .check(matches(atPositionOnView(0, withText("John Doe"), R.id.userName)))
    }

    @Test
    fun shouldLoadPreviousUsersWhenReachedToBottom() {
        overrideAppBindings {
            fakeAuthToken("secret")
            bind<RelatedUserListInteractor>(overrides = true) with instance(mock<RelatedUserListInteractor> {
                on { listFollowers(userId = 1, maxId = null) } doReturn Observable.just(listOf(
                    TestRelatedUser.copy(relationshipId = 1, name = "John Doe")
                ))
                on { listFollowers(userId = 1, maxId = 1) } doReturn Observable.just(listOf(
                    TestRelatedUser.copy(relationshipId = 0, name = "Old Follower")
                ))
                // just avoiding error
                on { listFollowers(userId = 1, maxId = 0) } doReturn Observable.empty()
            })
        }
        launchActivity(FOLLOWER, 1)

        onView(withId(R.id.userRecyclerView))
            .check(matches(atPositionOnView(0, withText("John Doe"), R.id.userName)))
        onView(withId(R.id.userRecyclerView))
            .check(matches(atPositionOnView(1, withText("Old Follower"), R.id.userName)))
    }

    @Test
    fun shouldFollowUserOnList() {
        overrideAppBindings {
            fakeAuthTokenAndBind("secret") {
                on { isMyself(anyOrNull()) } doReturn false // ensure button is shown
            }
            bind<RelatedUserListInteractor>(overrides = true) with instance(mock<RelatedUserListInteractor> {
                on { listFollowers(1, null) } doReturn Observable.just(listOf(
                    TestRelatedUser.copy(relationshipId = 1, isFollowedByMe = false) // Follow btn will be shown
                ))
                // just avoiding error
                on { listFollowers(userId = 1, maxId = 1) } doReturn Observable.empty()
            })
            bind<RelationshipInteractor>(overrides = true) with instance(mock<RelationshipInteractor> {
                on { follow(1) } doReturn Observable.just<Void>(null)
            })
        }
        launchActivity(FOLLOWER, 1)

        onView(withId(R.id.followBtn)).perform(click())
        onView(withId(R.id.followBtn)).check(matches(withText(R.string.Unfollow)))
    }

    @Test
    fun shouldNavigateToUserShowWhenAvatarClicked() {
        val navigator: Navigator = mock()
        overrideAppBindings {
            fakeAuthToken("secret")
            bind<RelatedUserListInteractor>(overrides = true) with instance(mock<RelatedUserListInteractor> {
                on { listFollowers(1, null) } doReturn Observable.just(listOf(
                    TestRelatedUser.copy(id = 100, relationshipId = 1)
                ))
                // just avoiding error
                on { listFollowers(userId = 1, maxId = 1) } doReturn Observable.empty()
            })
            bind<Navigator>(overrides = true) with instance(navigator)
        }
        launchActivity(FOLLOWER, 1)
        onView(withId(R.id.avatar)).perform(click())

        verify(navigator).navigateToUserShow(100)
    }

    // ------------- FOLLOWING part -------------

    @Test
    fun shouldBeOpenedWhenAuthenticatedAsFollowing() {
        overrideAppBindings {
            fakeAuthToken("secret")
            bind<RelatedUserListInteractor>(overrides = true) with instance(mock<RelatedUserListInteractor> {
                // just avoiding error
                on { listFollowings(userId = 1, maxId = null) } doReturn Observable.empty()
            })
        }
        launchActivity(FOLLOWING, 1)
        onView(allOf(
            isDescendantOfA(withResourceName("android:id/action_bar_container")),
            withText(R.string.Followings)
        ))
    }

    @Test
    fun shouldShowFollowings() {
        overrideAppBindings {
            fakeAuthToken("secret")
            bind<RelatedUserListInteractor>(overrides = true) with instance(mock<RelatedUserListInteractor> {
                on { listFollowings(1, null) } doReturn Observable.just(listOf(
                    TestRelatedUser.copy(relationshipId = 1, name = "John Doe")
                ))
                // just avoiding error
                on { listFollowings(userId = 1, maxId = 1) } doReturn Observable.empty()
            })
        }
        launchActivity(FOLLOWING, 1)

        onView(withId(R.id.userRecyclerView))
            .check(matches(atPositionOnView(0, withText("John Doe"), R.id.userName)))
    }

    private fun launchActivity(listType: RelatedUserListActivity.ListType, userId: Long) {
        val intent = Intent()
        intent.putExtra(RelatedUserListActivity.KEY_USER_ID, userId)
        intent.putExtra(RelatedUserListActivity.KEY_LIST_TYPE, listType)
        activityRule.launchActivity(intent)
    }
}