package com.hana053.micropost.services

import android.preference.PreferenceManager
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestUser
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class AuthTokenServiceTest : RobolectricBaseTest() {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

    private val authTokenService = AuthTokenServiceImpl(sharedPreferences)

    @Test
    fun shouldSaveAuthToken() {
        authTokenService.setAuthToken("my token")
        assertThat(authTokenService.getAuthToken(), `is`("my token"))
    }

    @Test
    fun shouldClearAuthToken() {
        authTokenService.setAuthToken("my token")
        authTokenService.clearAuthToken()
        assertThat(authTokenService.getAuthToken(), nullValue())
    }

    @Test
    fun shouldDetermineIsMyself() {
        // This token has user_id = 1
        authTokenService.setAuthToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNDc5NDUwNDY0fQ.Dy33qbg6EnP1bL2DmItMNGDEunrYP7-rzf586wxb2D-wW8WCsFrKdCeCU_ZHq_A7-kg_LxBykyaoG_26z-k9uA")

        val user1 = TestUser.copy(id = 1)
        assertThat(authTokenService.isMyself(user1), `is`(true))

        val user2 = TestUser.copy(id = 2)
        assertThat(authTokenService.isMyself(user2), `is`(false))
    }

}