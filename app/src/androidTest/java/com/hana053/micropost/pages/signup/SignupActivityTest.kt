package com.hana053.micropost.pages.signup

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.services.LoginService
import com.hana053.micropost.testing.InjectableTest
import com.hana053.myapp.testing.EmptyResponseBody
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.CoreMatchers.not
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import retrofit2.adapter.rxjava.HttpException
import rx.Observable


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

    @Test
    fun shouldInputName() {
        activityRule.launchActivity(null)

        onView(withId(R.id.fullNameNextBtn)).check(matches(not(isEnabled())))
        onView(withId(R.id.fullNameInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.fullName)).perform(typeText("a"))
        onView(withId(R.id.fullNameInvalid)).check(matches(isDisplayed()))
        onView(withId(R.id.fullNameNextBtn)).check(matches(not(isEnabled())))

        onView(withId(R.id.fullName)).perform(clearText(), typeText("John Doe"))
        onView(withId(R.id.fullNameNextBtn)).check(matches(isEnabled()))
        onView(withId(R.id.fullNameInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.fullNameNextBtn)).perform(closeSoftKeyboard(), click())
        onView(withText(R.string.what_s_your_email)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldInputEmail() {
        activityRule.launchActivity(null)

        moveToEmailWithName("John Doe")

        onView(withId(R.id.emailNextBtn)).check(matches(not(isEnabled())))
        onView(withId(R.id.emailInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.email)).perform(typeText("a"))
        onView(withId(R.id.emailInvalid)).check(matches(isDisplayed()))
        onView(withId(R.id.emailNextBtn)).check(matches(not(isEnabled())))

        onView(withId(R.id.email)).perform(clearText(), typeText("test@test.com"))
        onView(withId(R.id.emailNextBtn)).check(matches(isEnabled()))
        onView(withId(R.id.emailInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.emailNextBtn)).perform(closeSoftKeyboard(), click())
        onView(withText(R.string.you_ll_need_a_password)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldInputPasswordAndNavigateToMain() {
        putOverridingModule(SignupActivity::class.java, Kodein.Module {
            bind<SignupService>(overrides = true) with instance(mock<SignupService> {
                on { signup(any()) } doReturn Observable.just<Void>(null)
            })
        })
        overrideAppBindings {
            // required after launching MainActivity
            bind<LoginService>(overrides = true) with instance(mock<LoginService> {
                on { auth() } doReturn true
            })
        }
        activityRule.launchActivity(null)
        moveToEmailWithName("John Doe")
        moveToPasswordWithEmail("test@test.com")

        onView(withId(R.id.passwordNextBtn)).check(matches(not(isEnabled())))
        onView(withId(R.id.passwordInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.password)).perform(typeText("a"))
        onView(withId(R.id.passwordInvalid)).check(matches(isDisplayed()))
        onView(withId(R.id.passwordNextBtn)).check(matches(not(isEnabled())))

        onView(withId(R.id.password)).perform(clearText(), typeText("secret123"))
        onView(withId(R.id.passwordNextBtn)).check(matches(isEnabled()))
        onView(withId(R.id.passwordInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.passwordNextBtn)).perform(closeSoftKeyboard(), click())
        onView(withText(R.string.home)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNotNavigateToMainWhenEmailIsAlreadyTaken() {
        val error = Observable.error<Void>(HttpException(Response.error<Void>(400, EmptyResponseBody())))

        putOverridingModule(SignupActivity::class.java, Kodein.Module {
            bind<SignupService>(overrides = true) with instance(mock<SignupService> {
                on { signup(any()) } doReturn error
            })
        })
        activityRule.launchActivity(null)
        moveToEmailWithName("John Doe")
        moveToPasswordWithEmail("test@test.com")
        moveToMainWithPassword("secret123")

        onView(withText(R.string.what_s_your_email)).check(matches(isDisplayed()))
    }

    @Test
    @Ignore
    fun shouldNavigateToPreviousScreen() {
        activityRule.launchActivity(null)
        moveToEmailWithName("John Doe")
        moveToPasswordWithEmail("test@test.com")
        // FIXME pressBack ignores onBackPressed. https://github.com/prt2121/android-test-kit/issues/163
        pressBack()
        onView(withText(R.string.what_s_your_email)).check(matches(isDisplayed()))
    }

    private fun moveToEmailWithName(name: String) {
        onView(withId(R.id.fullName)).perform(typeText(name))
        onView(withId(R.id.fullNameNextBtn)).perform(closeSoftKeyboard(), click())
    }

    private fun moveToPasswordWithEmail(email: String) {
        onView(withId(R.id.email)).perform(typeText(email))
        onView(withId(R.id.emailNextBtn)).perform(closeSoftKeyboard(), click())
    }

    private fun moveToMainWithPassword(password: String) {
        onView(withId(R.id.password)).perform(typeText(password))
        onView(withId(R.id.passwordNextBtn)).perform(closeSoftKeyboard(), click())
    }


}