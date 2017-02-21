package com.hana053.micropost.pages.usershow

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.interactor.RelationshipInteractor
import com.hana053.micropost.interactor.UserInteractor
import com.hana053.micropost.interactor.UserMicropostInteractor
import com.hana053.micropost.service.AuthService
import com.hana053.micropost.service.Navigator
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
            fakeAuthToken()
            fakeLoadingPosts()
            fakeLoadingDetail()
        }
        launchActivityWithUserId(1)
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
            fakeAuthToken()
            fakeLoadingPosts()
            bind<UserInteractor>(overrides = true) with instance(mock<UserInteractor> {
                on { get(1) } doReturn Observable.just(TestUser.copy(name = "John Doe"))
            })
        }
        launchActivityWithUserId(1)
        onView(withId(R.id.tv_user_name)).check(matches(withText("John Doe")))
    }

    @Test
    fun shouldFollowAndReloadUser() {
        val userInteractor = mock<UserInteractor> {
            on { get(1) } doReturn Observable.just(
                TestUser.copy(name = "John Doe", isFollowedByMe = false)
            )
        }
        overrideAppBindings {
            fakeAuthToken()
            fakeLoadingPosts()
            bind<AuthService>(overrides = true) with instance(mock<AuthService> {
                on { auth() } doReturn true
                on { isMyself(anyOrNull()) } doReturn false // ensure button is shown
            })
            bind<UserInteractor>(overrides = true) with instance(userInteractor)
            bind<RelationshipInteractor>(overrides = true) with instance(mock<RelationshipInteractor> {
                on { follow(1) } doReturn Observable.just<Void>(null)
            })
        }

        launchActivityWithUserId(1)
        onView(withId(R.id.btn_follow)).check(matches(withText(R.string.Follow)))

        // Do Follow. It will finally fetch user again.
        `when`(userInteractor.get(1)).thenReturn(Observable.just(
            TestUser.copy(name = "John Doe", isFollowedByMe = true)
        ))
        onView(withId(R.id.btn_follow)).perform(click())
        onView(withId(R.id.btn_follow)).check(matches(withText(R.string.Unfollow)))
    }

    @Test
    fun shouldNavigateToFollowers() {
        val navigator: Navigator = mock()
        overrideAppBindings {
            fakeAuthToken()
            fakeLoadingPosts()
            fakeLoadingDetail()
            bind<Navigator>(overrides = true) with instance(navigator)
        }

        launchActivityWithUserId(1)
        onView(withId(R.id.tv_followers)).perform(click())

        verify(navigator).navigateToFollowerList(1)
    }

    @Test
    fun shouldNavigateToFollowings() {
        val navigator: Navigator = mock()
        overrideAppBindings {
            fakeAuthToken()
            fakeLoadingPosts()
            fakeLoadingDetail()
            bind<Navigator>(overrides = true) with instance(navigator)
        }

        launchActivityWithUserId(1)
        onView(withId(R.id.tv_followings)).perform(click())

        verify(navigator).navigateToFollowingList(1)
    }

    // ----------- Posts View part -----------

    @Test
    fun shouldShowPosts() {
        overrideAppBindings {
            fakeAuthToken()
            fakeLoadingDetail()
            bind<UserMicropostInteractor>(overrides = true) with instance(mock<UserMicropostInteractor> {
                on { loadPrevPosts(1, null) } doReturn Observable.just(listOf(
                    TestMicropost.copy(content = "my test content")
                ))
            })
        }

        launchActivityWithUserId(1)

        onView(withId(R.id.list_post))
            .check(matches(atPositionOnView(0, withText("my test content"), R.id.tv_post_content)))
    }

    private fun launchActivityWithUserId(userId: Long) {
        activityRule.launchActivity(Intent().putExtra(UserShowActivity.KEY_USER_ID, userId))
    }

    private fun Kodein.Builder.fakeLoadingDetail() {
        bind<UserInteractor>(overrides = true) with instance(mock<UserInteractor> {
            on { get(any()) } doReturn Observable.just(TestUser)
        })
    }

    private fun Kodein.Builder.fakeLoadingPosts() {
        bind<UserMicropostInteractor>(overrides = true) with instance(mock<UserMicropostInteractor> {
            on { loadPrevPosts(any(), anyOrNull()) } doReturn Observable.empty()
        })
    }

}