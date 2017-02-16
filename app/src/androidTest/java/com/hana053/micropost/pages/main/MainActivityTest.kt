package com.hana053.micropost.pages.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.services.AuthTokenService
import com.hana053.micropost.testing.InjectableTest
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


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
        overrideAppBindings {
            bind<AuthTokenService>(overrides = true) with instance(mock<AuthTokenService> {
                on { getAuthToken() } doReturn ""
            })
        }
        activityRule.launchActivity(null)
        // Open Top Page instead
        onView(withText(R.string.welcome_to_micropost)).check(matches(isDisplayed()))
    }


}