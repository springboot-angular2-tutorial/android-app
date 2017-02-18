package com.hana053.micropost.services

import android.app.Activity
import android.content.Intent
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.interactors.LoginInteractor
import com.hana053.micropost.pages.top.TopActivity
import com.hana053.micropost.testing.RobolectricBaseTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import rx.Observable


class LoginServiceTest : RobolectricBaseTest() {

    private val activity by lazy { Robolectric.setupActivity(Activity::class.java) }
    private val loginService by lazy { app.appKodein().instance<LoginService>() }

    @Test
    fun shouldLogin() {
        overrideAppBindingsWithActivity(activity) {
            bind<LoginInteractor>(overrides = true) with instance(mock<LoginInteractor> {
                on { login(any()) } doReturn Observable.just(LoginInteractor.LoginResponse("my token"))
            })
            bind<AuthTokenService>(overrides = true) with instance(mock<AuthTokenService>())
        }

        loginService.login("test@test.com", "secret").subscribe()
        advance()

        val authTokenService = app.appKodein().instance<AuthTokenService>()
        verify(authTokenService).setAuthToken("my token")
    }

    @Test
    fun shouldLogout() {
        overrideAppBindingsWithActivity(activity) {
            bind<AuthTokenService>(overrides = true) with instance(mock<AuthTokenService>())
        }

        loginService.logout()

        val authTokenService = app.appKodein().instance<AuthTokenService>()
        verify(authTokenService).clearAuthToken()

        val shadow = Shadows.shadowOf(RuntimeEnvironment.application)
        val intent = shadow.nextStartedActivity
        assertThat(intent.flags, `is`(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        assertThat(TopActivity::class.java.name, `is`(intent.component.className))
    }

    @Test
    fun shouldForceLogoutWhenAuthTokenIsEmpty() {
        overrideAppBindingsWithActivity(activity)

        assertThat(loginService.auth(), `is`(false))

        val shadow = Shadows.shadowOf(RuntimeEnvironment.application)
        val intent = shadow.nextStartedActivity
        assertThat(TopActivity::class.java.name, `is`(intent.component.className))
    }

    @Test
    fun shouldJustReturnTrueWhenAuthenticated() {
        overrideAppBindingsWithActivity(activity) {
            bind<AuthTokenService>(overrides = true) with instance(mock<AuthTokenService> {
                on { getAuthToken() } doReturn "test token"
            })
        }

        assertThat(loginService.auth(), `is`(true))
    }

}