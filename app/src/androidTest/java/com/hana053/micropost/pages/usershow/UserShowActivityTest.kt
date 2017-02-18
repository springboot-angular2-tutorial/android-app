package com.hana053.micropost.pages.usershow

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.interactors.RelationshipInteractor
import com.hana053.micropost.interactors.UserInteractor
import com.hana053.micropost.interactors.UserMicropostInteractor
import com.hana053.micropost.testing.*
import com.nhaarman.mockito_kotlin.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import rx.Observable


@RunWith(AndroidJUnit4::class)
@LargeTest
class UserShowActivityTest : InjectableTest by InjectableTestImpl() {

    @Rule @JvmField
    val activityRule = ActivityTestRule(UserShowActivity::class.java, false, false)

    @Test
    fun shouldBeOpenedWhenAuthenticated() {
        overrideAppBindings {
            fakeAuthToken("secret")
            fakeLoadingPosts()
            fakeLoadingDetail()
        }
        launchActivityWithUserId(1)
        onView(withText(R.string.followers)).check(matches(isDisplayed()))
        onView(withText(R.string.followings)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNotBeOpenedWhenNotAuthenticated() {
        val navigator = mock<Navigator>()
        overrideAppBindings {
            bind<Navigator>(overrides = true) with instance(navigator)
        }
        launchActivityWithUserId(1)
        verify(navigator).navigateToTop()
    }

    // ----------- Detail View part -----------

    @Test
    fun shouldShowUser() {
        overrideAppBindings {
            fakeAuthToken("secret")
            fakeLoadingPosts()
            bind<UserInteractor>(overrides = true) with instance(mock<UserInteractor> {
                on { get(1) } doReturn Observable.just(TestUser.copy(name = "John Doe"))
            })
        }
        launchActivityWithUserId(1)
        onView(withText("John Doe")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldFollowAndReloadUser() {
        val userInteractor = mock<UserInteractor> {
            on { get(1) } doReturn Observable.just(
                TestUser.copy(name = "John Doe", isFollowedByMe = false)
            )
        }
        overrideAppBindings {
            fakeAuthTokenAndBind("secret") {
                on { isMyself(anyOrNull()) } doReturn false // ensure button is shown
            }
            fakeLoadingPosts()
            bind<UserInteractor>(overrides = true) with instance(userInteractor)
            bind<RelationshipInteractor>(overrides = true) with instance(mock<RelationshipInteractor> {
                on { follow(1) } doReturn Observable.just<Void>(null)
            })
        }

        launchActivityWithUserId(1)
        onView(withId(R.id.followBtn)).check(matches(withText(R.string.Follow)))

        // Do Follow. It will finally fetch user again.
        `when`(userInteractor.get(1)).thenReturn(Observable.just(
            TestUser.copy(name = "John Doe", isFollowedByMe = true)
        ))
        onView(withId(R.id.followBtn)).perform(click())
        onView(withId(R.id.followBtn)).check(matches(withText(R.string.Unfollow)))
    }

    @Test
    fun shouldNavigateToFollowers() {
        val navigator: Navigator = mock()
        overrideAppBindings {
            fakeAuthToken("secret")
            fakeLoadingPosts()
            fakeLoadingDetail()
            bind<Navigator>(overrides = true) with instance(navigator)
        }

        launchActivityWithUserId(1)
        onView(withId(R.id.followers)).perform(click())

        verify(navigator).navigateToFollowerList(1)
    }

    @Test
    fun shouldNavigateToFollowings() {
        val navigator: Navigator = mock()
        overrideAppBindings {
            fakeAuthToken("secret")
            fakeLoadingPosts()
            fakeLoadingDetail()
            bind<Navigator>(overrides = true) with instance(navigator)
        }

        launchActivityWithUserId(1)
        onView(withId(R.id.followings)).perform(click())

        verify(navigator).navigateToFollowingList(1)
    }

    // ----------- Posts View part -----------

    @Test
    fun shouldShowPosts() {
        overrideAppBindings {
            fakeAuthToken("secret")
            fakeLoadingDetail()
            bind<UserMicropostInteractor>(overrides = true) with instance(mock<UserMicropostInteractor> {
                on { loadPrevPosts(1, null) } doReturn Observable.just(listOf(
                    TestMicropost.copy(content = "my test content")
                ))
            })
        }

        launchActivityWithUserId(1)

        onView(withId(R.id.postRecyclerView))
            .check(matches(atPositionOnView(0, withText("my test content"), R.id.content)))
    }

    private fun launchActivityWithUserId(userId: Long) {
        activityRule.launchActivity(Intent().putExtra(UserShowActivity.KEY_USER_ID, userId))
    }

    private fun Kodein.Builder.fakeLoadingDetail() {
        bind<UserInteractor>(overrides = true) with instance(mock<UserInteractor> {
            on { get(any()) } doReturn Observable.empty()
        })
    }

    private fun Kodein.Builder.fakeLoadingPosts() {
        bind<UserMicropostInteractor>(overrides = true) with instance(mock<UserMicropostInteractor> {
            on { loadPrevPosts(any(), anyOrNull()) } doReturn Observable.empty()
        })
    }

}