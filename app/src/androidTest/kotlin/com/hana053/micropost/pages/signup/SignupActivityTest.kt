package com.hana053.micropost.pages.signup

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
import com.hana053.micropost.content
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
import kotlinx.android.synthetic.main.signup_email.view.*
import kotlinx.android.synthetic.main.signup_full_name.view.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import retrofit2.adapter.rxjava.HttpException
import rx.Observable


@RunWith(AndroidJUnit4::class)
@LargeTest
class SignupActivityTest : InjectableTest by InjectableTestImpl() {

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

        onView(withId(R.id.fullName)).perform(typeText("a"), closeSoftKeyboard())
        onView(withId(R.id.fullNameInvalid)).check(matches(isDisplayed()))
        onView(withId(R.id.fullNameNextBtn)).check(matches(not(isEnabled())))

        onView(withId(R.id.fullName)).perform(replaceText("John Doe"), closeSoftKeyboard())
        onView(withId(R.id.fullNameNextBtn)).check(matches(isEnabled()))
        onView(withId(R.id.fullNameInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.fullNameNextBtn)).perform(click())
        onView(withText(R.string.what_s_your_email)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldInputEmail() {
        activityRule.launchActivity(null)

        moveToEmailWithName("John Doe")

        onView(withId(R.id.emailNextBtn)).check(matches(not(isEnabled())))
        onView(withId(R.id.emailInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.email)).perform(typeText("a"), closeSoftKeyboard())
        onView(withId(R.id.emailInvalid)).check(matches(isDisplayed()))
        onView(withId(R.id.emailNextBtn)).check(matches(not(isEnabled())))

        onView(withId(R.id.email)).perform(replaceText("test@test.com"), closeSoftKeyboard())
        onView(withId(R.id.emailNextBtn)).check(matches(isEnabled()))
        onView(withId(R.id.emailInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.emailNextBtn)).perform(click())
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

        onView(withId(R.id.passwordNextBtn)).check(matches(not(isEnabled())))
        onView(withId(R.id.passwordInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.password)).perform(typeText("a"), closeSoftKeyboard())
        onView(withId(R.id.passwordInvalid)).check(matches(isDisplayed()))
        onView(withId(R.id.passwordNextBtn)).check(matches(not(isEnabled())))

        onView(withId(R.id.password)).perform(replaceText("secret123"), closeSoftKeyboard())
        onView(withId(R.id.passwordNextBtn)).check(matches(isEnabled()))
        onView(withId(R.id.passwordInvalid)).check(matches(not(isDisplayed())))

        onView(withId(R.id.passwordNextBtn)).perform(click())

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

        val content = activityRule.activity.content()

        onView(withId(android.R.id.content)).perform(pressBack())
        onView(withText(R.string.what_s_your_email)).check(matches(isDisplayed()))
        assertThat(content.email.text.toString(), `is`("test@test.com"))

        onView(withId(android.R.id.content)).perform(pressBack())
        onView(withText(R.string.hi_what_s_your_name)).check(matches(isDisplayed()))
        assertThat(content.fullName.text.toString(), `is`("John Doe"))
    }

    private fun moveToEmailWithName(name: String) {
        onView(withId(R.id.fullName)).perform(typeText(name), closeSoftKeyboard())
        onView(withId(R.id.fullNameNextBtn)).perform(click())
    }

    private fun moveToPasswordWithEmail(email: String) {
        onView(withId(R.id.email)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.emailNextBtn)).perform(click())
    }

    private fun moveToMainWithPassword(password: String) {
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.passwordNextBtn)).perform(click())
    }

}