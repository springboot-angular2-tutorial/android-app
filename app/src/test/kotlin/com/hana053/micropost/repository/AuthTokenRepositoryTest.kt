package com.hana053.micropost.repository

import android.preference.PreferenceManager
import com.hana053.micropost.testing.RobolectricBaseTest
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
        authTokenRepository.set("my token")
        assertThat(authTokenRepository.get(), `is`("my token"))
    }

    @Test
    fun shouldClearAuthToken() {
        authTokenRepository.set("my token")
        authTokenRepository.clear()
        assertThat(authTokenRepository.get(), nullValue())
    }

}