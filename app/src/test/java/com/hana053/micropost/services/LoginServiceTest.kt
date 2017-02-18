package com.hana053.micropost.services

import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.interactors.LoginInteractor
import com.hana053.micropost.testing.RobolectricBaseTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import rx.Observable


class LoginServiceTest : RobolectricBaseTest() {

    private val loginInteractor = mock<LoginInteractor>()
    private val authTokenService = mock<AuthTokenService>()
    private val navigator = mock<Navigator>()
    private val loginService = LoginServiceImpl(loginInteractor, authTokenService, navigator)

    @Test
    fun shouldLogin() {
        `when`(loginInteractor.login(any()))
            .doReturn(Observable.just(LoginInteractor.LoginResponse("my token")))

        loginService.login("test@test.com", "secret").subscribe()
        advance()

        verify(authTokenService).setAuthToken("my token")
    }

    @Test
    fun shouldLogout() {
        loginService.logout()

        verify(authTokenService).clearAuthToken()
        verify(navigator).navigateToTop()
    }

    @Test
    fun shouldForceLogoutWhenAuthTokenIsEmpty() {
        assertThat(loginService.auth(), `is`(false))
        verify(navigator).navigateToTop()
    }

    @Test
    fun shouldJustReturnTrueWhenAuthenticated() {
        `when`(authTokenService.getAuthToken()).doReturn("test token")

        assertThat(loginService.auth(), `is`(true))
    }

}