package com.hana053.micropost.pages.top

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.hana053.micropost.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class TopActivityTest1 {


    @Rule @JvmField
    val activityRule = ActivityTestRule(TopActivity::class.java)

    @Test
    fun shouldBeOpened() {
        onView(withText(R.string.welcome_to_micropost)).check(matches(isDisplayed()))
    }

//    @Test
//    fun shouldNavigateToSignup() {
//        onView(withId(R.id.signupBtn)).perform(click())
//        onView(withText(R.string.hi_what_s_your_name)).check(matches(isDisplayed()))
//    }

    @Test
    fun shouldNavigateToLogin() {
        onView(withId(R.id.loginBtn)).perform(click())
        onView(withText(R.string.log_in_to_micropost)).check(matches(isDisplayed()))
    }

}
