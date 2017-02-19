package com.hana053.micropost.services

import android.preference.PreferenceManager
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestUser
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.robolectric.RuntimeEnvironment

class AuthTokenRepositoryTest : RobolectricBaseTest() {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RuntimeEnvironment.application)
    private val authTokenRepository = AuthTokenRepositoryImpl(sharedPreferences)

    @Test
    fun shouldSaveAuthToken() {
        authTokenRepository.setAuthToken("my token")
        assertThat(authTokenRepository.getAuthToken(), `is`("my token"))
    }

    @Test
    fun shouldClearAuthToken() {
        authTokenRepository.setAuthToken("my token")
        authTokenRepository.clearAuthToken()
        assertThat(authTokenRepository.getAuthToken(), nullValue())
    }

}