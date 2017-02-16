package com.hana053.micropost.pages.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeDown
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
import com.hana053.micropost.services.AuthTokenService
import com.hana053.micropost.services.LoginService
import com.hana053.micropost.testing.InjectableTest
import com.hana053.micropost.testing.TestMicropost
import com.hana053.myapp.interactors.FeedInteractor
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest : InjectableTest() {

    @Rule @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun shouldBeOpenedWhenAuthenticated() {
        overrideAppBindings {
            bind<AuthTokenService>(overrides = true) with instance(mock<AuthTokenService> {
                on { getAuthToken() } doReturn "token123"
            })
        }

        activityRule.launchActivity(null)

        onView(withText(R.string.home)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNotBeOpenedWhenNotAuthenticated() {
        val navigator = mock<Navigator>()
        overrideAppBindings {
            bind<AuthTokenService>(overrides = true) with instance(mock<AuthTokenService> {
                on { getAuthToken() } doReturn ""
            })
            bind<Navigator>(overrides = true) with instance(navigator)
        }

        activityRule.launchActivity(null)

        verify(navigator).navigateToTop()
    }

    @Test
    fun shouldShowFeed() {
        overrideAppBindings {
            fakeAuth()
            bind<FeedInteractor>(overrides = true) with instance(mock<FeedInteractor> {
                on { loadNextFeed(null) } doReturn Observable.just(listOf(
                    TestMicropost.copy(content = "my test content")
                ))
                // just avoid exception
                on { loadPrevFeed(anyOrNull()) } doReturn Observable.empty()
            })
        }

        activityRule.launchActivity(null)

        onView(withText("my test content")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldLoadNextFeedWhenSwipeRefreshed() {
        overrideAppBindings {
            fakeAuth()
            bind<FeedInteractor>(overrides = true) with instance(mock<FeedInteractor> {
                on { loadNextFeed(null) } doReturn Observable.just(listOf(
                    TestMicropost.copy(id = 1, content = "my test content")
                ))
                on { loadNextFeed(1) } doReturn Observable.just(listOf(
                    TestMicropost.copy(id = 2, content = "my new content")
                ))
                // just avoid exception
                on { loadPrevFeed(anyOrNull()) } doReturn Observable.empty()
            })
        }

        activityRule.launchActivity(null)
        onView(withId(R.id.swipeRefreshLayout)).perform(swipeDown())

        onView(withText("my new content")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldLoadPreviousFeedWhenReachedToBottom() {
        overrideAppBindings {
            fakeAuth()
            bind<FeedInteractor>(overrides = true) with instance(mock<FeedInteractor> {
                on { loadNextFeed(null) } doReturn Observable.just(listOf(
                    TestMicropost.copy(id = 1, content = "my test content")
                ))
                on { loadPrevFeed(1) } doReturn Observable.just(listOf(
                    TestMicropost.copy(id = 0, content = "my old content")
                ))
                // just avoid exception
                on { loadPrevFeed(0) } doReturn Observable.empty()
            })
        }

        activityRule.launchActivity(null)

        onView(withText("my old content")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNavigateToUserShowWhenAvatarClicked() {
        val navigator = mock<Navigator>()
        overrideAppBindings {
            fakeAuth()
            bind<Navigator>(overrides = true) with instance(navigator)
            bind<FeedInteractor>(overrides = true) with instance(mock<FeedInteractor> {
                on { loadNextFeed(null) } doReturn Observable.just(listOf(
                    TestMicropost.copy(id = 1, content = "my test content")
                ))
                // just avoid exception
                on { loadPrevFeed(anyOrNull()) } doReturn Observable.empty()
            })
        }

        activityRule.launchActivity(null)
        onView(withId(R.id.avatar)).perform(click())

        verify(navigator).navigateToUserShow(1)
    }

    @Test
    fun shouldNavigateToMicropostNewWhenNewMicropostBtnClicked() {
        val navigator = mock<Navigator>()
        overrideAppBindings {
            fakeAuth()
            bind<Navigator>(overrides = true) with instance(navigator)
            bind<FeedInteractor>(overrides = true) with instance(mock<FeedInteractor> {
                // just avoid exception
                on { loadNextFeed(anyOrNull()) } doReturn Observable.empty()
                on { loadPrevFeed(anyOrNull()) } doReturn Observable.empty()
            })
        }

        activityRule.launchActivity(null)
        onView(withId(R.id.newMicropostBtn)).perform(click())

        verify(navigator).navigateToMicropostNew()
    }

    private fun Kodein.Builder.fakeAuth() {
        bind<LoginService>(overrides = true) with instance(mock<LoginService> {
            on { auth() } doReturn true
        })
    }


}