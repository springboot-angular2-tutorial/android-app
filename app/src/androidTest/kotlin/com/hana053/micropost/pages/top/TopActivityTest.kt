package com.hana053.micropost.pages.top

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
import com.hana053.micropost.service.Navigator
import com.hana053.micropost.testing.InjectableTest
import com.hana053.micropost.testing.InjectableTestImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class TopActivityTest : InjectableTest by InjectableTestImpl() {

    @Rule @JvmField
    val activityRule = ActivityTestRule(TopActivity::class.java, false, false)

    @Test
    fun shouldBeOpened() {
        activityRule.launchActivity(null)

        onView(withText(R.string.welcome_to_micropost)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNavigateToSignup() {
        val navigator = mock<Navigator>()
        overrideAppBindings {
            bind<Navigator>(overrides = true) with instance(navigator)
        }

        activityRule.launchActivity(null)
        onView(withId(R.id.btn_signup)).perform(click())

        verify(navigator).navigateToSignup()
    }

    @Test
    fun shouldNavigateToLogin() {
        val navigator = mock<Navigator>()
        overrideAppBindings {
            bind<Navigator>(overrides = true) with instance(navigator)
        }

        activityRule.launchActivity(null)
        onView(withId(R.id.btn_login)).perform(click())

        verify(navigator).navigateToLogin()
    }

}
