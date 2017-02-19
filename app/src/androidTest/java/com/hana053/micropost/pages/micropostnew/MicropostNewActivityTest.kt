package com.hana053.micropost.pages.micropostnew

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.interactor.MicropostInteractor
import com.hana053.micropost.service.Navigator
import com.hana053.micropost.testing.InjectableTest
import com.hana053.micropost.testing.InjectableTestImpl
import com.hana053.micropost.testing.TestMicropost
import com.hana053.micropost.testing.fakeAuthToken
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable


@RunWith(AndroidJUnit4::class)
@LargeTest
class MicropostNewActivityTest : InjectableTest by InjectableTestImpl() {

    @Rule @JvmField
    val activityRule = ActivityTestRule(MicropostNewActivity::class.java, false, false)

    @Test
    fun shouldBeOpenedWhenAuthenticated() {
        overrideAppBindings { fakeAuthToken() }
        activityRule.launchActivity(null)
        onView(withText(R.string.post)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNotBeOpenedWhenNotAuthenticated() {
        val navigator = mock<Navigator>()
        overrideAppBindings {
            bind<Navigator>(overrides = true) with instance(navigator)
        }
        activityRule.launchActivity(null)
        verify(navigator).navigateToTop()
    }

    @Test
    fun shouldDisableOrEnableBtn() {
        overrideAppBindings { fakeAuthToken() }
        activityRule.launchActivity(null)

        onView(withId(R.id.postBtn)).check(matches(not(isEnabled())))

        onView(withId(R.id.postEditText)).perform(typeText("a"))
        onView(withId(R.id.postBtn)).check(matches(isEnabled()))
    }

    @Test
    fun shouldPostAndNavigateToMain() {
        val micropostInteractor: MicropostInteractor = mock {
            on { create(any()) } doReturn Observable.just(TestMicropost)
        }
        overrideAppBindings {
            fakeAuthToken()
            bind<MicropostInteractor>(overrides = true) with instance(micropostInteractor)
        }
        activityRule.launchActivity(null)

        onView(withId(R.id.postEditText)).perform(typeText("a"))
        onView(withId(R.id.postBtn)).perform(closeSoftKeyboard(), click())

        verify(micropostInteractor).create(MicropostInteractor.MicropostRequest("a"))
        assertThat(activityRule.activity.isFinishing, `is`(true))
    }

}