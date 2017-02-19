package com.hana053.micropost.service

import com.hana053.micropost.repository.AuthTokenRepository
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestUser
import com.hana053.micropost.testing.jwtForUserId1
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`


class AuthServiceTest : RobolectricBaseTest() {

    private val navigator: Navigator = mock()
    private val authTokenRepository: AuthTokenRepository = mock()
    private val authService = AuthServiceImpl(
        authTokenRepository = authTokenRepository,
        navigator = navigator
    )

    @Test
    fun shouldDetermineIsMyself() {
        `when`(authTokenRepository.getAuthToken())
            .doReturn(jwtForUserId1)

        val user1 = TestUser.copy(id = 1)
        assertThat(authService.isMyself(user1), `is`(true))

        val user2 = TestUser.copy(id = 2)
        assertThat(authService.isMyself(user2), `is`(false))
    }

    @Test
    fun shouldLogout() {
        authService.logout()

        verify(authTokenRepository).clearAuthToken()
        verify(navigator).navigateToTop()
    }

    @Test
    fun shouldForceLogoutWhenAuthTokenIsEmpty() {
        assertThat(authService.auth(), `is`(false))
        verify(navigator).navigateToTop()
    }

    @Test
    fun shouldJustReturnTrueWhenAuthenticated() {
        `when`(authTokenRepository.getAuthToken()).doReturn("test token")
        assertThat(authService.auth(), `is`(true))
    }
}