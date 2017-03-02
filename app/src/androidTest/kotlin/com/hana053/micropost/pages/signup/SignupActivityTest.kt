package com.hana053.micropost.pages.signup

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.domain.User
import com.hana053.micropost.interactor.UserInteractor
import com.hana053.micropost.service.LoginService
import com.hana053.micropost.service.Navigator
import com.hana053.micropost.testing.EmptyResponseBody
import com.hana053.micropost.testing.InjectableTest
import com.hana053.micropost.testing.InjectableTestImpl
import com.hana053.micropost.testing.TestUser
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import kotlinx.android.synthetic.main.fragment_signup_email.*
import kotlinx.android.synthetic.main.fragment_signup_full_name.*
import org.assertj.android.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import retrofit2.Response
import rx.Observable


@RunWith(AndroidJUnit4::class)
@LargeTest
class SignupActivityTest : InjectableTest by InjectableTestImpl() {

    @Rule @JvmField
    val activityRule = ActivityTestRule(SignupActivity::class.java, false, false)

    val fullNameNextBtn: Matcher<View> = allOf(
        withId(R.id.btn_next),
        isDescendantOfA(withId(R.id.fragment_signup_full_name))
    )
    val emailNextBtn: Matcher<View> = allOf(
        withId(R.id.btn_next),
        isDescendantOfA(withId(R.id.fragment_signup_email))
    )
    val passwordNextBtn: Matcher<View> = allOf(
        withId(R.id.btn_next),
        isDescendantOfA(withId(R.id.fragment_signup_password))
    )

    @Test
    fun shouldBeOpened() {
        activityRule.launchActivity(null)
        onView(withText(R.string.hi_what_s_your_name)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldInputName() {
        activityRule.launchActivity(null)

        onView(fullNameNextBtn).check(matches(not(isEnabled())))
        onView(withId(R.id.tv_full_name_invalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.et_full_name)).perform(typeText("a"), closeSoftKeyboard())
        onView(withId(R.id.tv_full_name_invalid)).check(matches(isDisplayed()))
        onView(fullNameNextBtn).check(matches(not(isEnabled())))

        onView(withId(R.id.et_full_name)).perform(replaceText("John Doe"), closeSoftKeyboard())
        onView(fullNameNextBtn).check(matches(isEnabled()))
        onView(withId(R.id.tv_full_name_invalid)).check(matches(not(isDisplayed())))

        onView(fullNameNextBtn).perform(click())
        onView(withText(R.string.what_s_your_email)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldInputEmail() {
        activityRule.launchActivity(null)

        moveToEmailWithName("John Doe")

        onView(emailNextBtn).check(matches(not(isEnabled())))
        onView(withId(R.id.tv_email_invalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.et_email)).perform(typeText("a"), closeSoftKeyboard())
        onView(withId(R.id.tv_email_invalid)).check(matches(isDisplayed()))
        onView(emailNextBtn).check(matches(not(isEnabled())))

        onView(withId(R.id.et_email)).perform(replaceText("test@test.com"), closeSoftKeyboard())
        onView(emailNextBtn).check(matches(isEnabled()))
        onView(withId(R.id.tv_email_invalid)).check(matches(not(isDisplayed())))

        onView(emailNextBtn).perform(click())
        onView(withText(R.string.you_ll_need_a_password)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldInputPasswordAndNavigateToMain() {
        val navigator = mock<Navigator>()
        overrideAppBindings {
            bind<Navigator>(overrides = true) with instance(navigator)
            bind<UserInteractor>(overrides = true) with instance(mock<UserInteractor> {
                on { create(any()) } doReturn Observable.just(TestUser)
            })
            bind<LoginService>(overrides = true) with instance(mock<LoginService> {
                on { login(any(), any()) } doReturn Observable.just<Void>(null)
            })
        }

        activityRule.launchActivity(null)
        moveToEmailWithName("John Doe")
        moveToPasswordWithEmail("test@test.com")

        onView(passwordNextBtn).check(matches(not(isEnabled())))
        onView(withId(R.id.tv_password_invalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.et_password)).perform(typeText("a"), closeSoftKeyboard())
        onView(withId(R.id.tv_password_invalid)).check(matches(isDisplayed()))
        onView(passwordNextBtn).check(matches(not(isEnabled())))

        onView(withId(R.id.et_password)).perform(replaceText("secret123"), closeSoftKeyboard())
        onView(passwordNextBtn).check(matches(isEnabled()))
        onView(withId(R.id.tv_password_invalid)).check(matches(not(isDisplayed())))

        onView(passwordNextBtn).perform(click())

        verify(navigator).navigateToMain()
    }

    @Test
    fun shouldNotNavigateToMainWhenEmailIsAlreadyTaken() {
        val error = Observable.error<User>(HttpException(Response.error<Void>(400, EmptyResponseBody())))
        overrideAppBindings {
            bind<UserInteractor>(overrides = true) with instance(mock<UserInteractor> {
                on { create(any()) } doReturn error
            })
        }

        activityRule.launchActivity(null)
        moveToEmailWithName("John Doe")
        moveToPasswordWithEmail("test@test.com")
        moveToMainWithPassword("secret123")

        onView(withText(R.string.what_s_your_email)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNavigateToPreviousScreen() {
        activityRule.launchActivity(null)
        moveToEmailWithName("John Doe")
        moveToPasswordWithEmail("test@test.com")

        val activity = activityRule.activity

        onView(withId(android.R.id.content)).perform(pressBack())
        onView(withText(R.string.what_s_your_email)).check(matches(isDisplayed()))
        assertThat(activity.et_email).hasTextString("test@test.com")

        onView(withId(android.R.id.content)).perform(pressBack())
        onView(withText(R.string.hi_what_s_your_name)).check(matches(isDisplayed()))
        assertThat(activity.et_full_name).hasTextString("John Doe")
    }

    private fun moveToEmailWithName(name: String) {
        onView(withId(R.id.et_full_name)).perform(typeText(name), closeSoftKeyboard())
        onView(fullNameNextBtn).perform(click())
    }

    private fun moveToPasswordWithEmail(email: String) {
        onView(withId(R.id.et_email)).perform(typeText(email), closeSoftKeyboard())
        onView(emailNextBtn).perform(click())
    }

    private fun moveToMainWithPassword(password: String) {
        onView(withId(R.id.et_password)).perform(typeText(password), closeSoftKeyboard())
        onView(passwordNextBtn).perform(click())
    }

}