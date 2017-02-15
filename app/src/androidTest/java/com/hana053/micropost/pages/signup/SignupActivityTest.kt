package com.hana053.micropost.pages.signup

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.hana053.micropost.R
import com.hana053.micropost.testing.InjectableTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class SignupActivityTest : InjectableTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule(SignupActivity::class.java, false, false)

    @Test
    fun shouldBeOpened() {
        activityRule.launchActivity(null)
        onView(withText(R.string.hi_what_s_your_name)).check(matches(isDisplayed()))
    }

}