package com.hana053.micropost.service

import com.hana053.micropost.repository.AuthTokenRepository
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestUser
import com.hana053.micropost.testing.jwtForUserId1
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
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
        `when`(authTokenRepository.get())
            .doReturn(jwtForUserId1)

        val user1 = TestUser.copy(id = 1)
        assertThat(authService.isMyself(user1)).isTrue()

        val user2 = TestUser.copy(id = 2)
        assertThat(authService.isMyself(user2)).isFalse()
    }

    @Test
    fun shouldLogout() {
        authService.logout()

        verify(authTokenRepository).clear()
        verify(navigator).navigateToTop()
    }

    @Test
    fun shouldForceLogoutWhenAuthTokenIsEmpty() {
        assertThat(authService.auth()).isFalse()
        verify(navigator).navigateToTop()
    }

    @Test
    fun shouldJustReturnTrueWhenAuthenticated() {
        `when`(authTokenRepository.get()).doReturn("test token")
        assertThat(authService.auth()).isTrue()
    }
}