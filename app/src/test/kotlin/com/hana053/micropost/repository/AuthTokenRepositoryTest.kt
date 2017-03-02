package com.hana053.micropost.repository

import android.preference.PreferenceManager
import com.hana053.micropost.testing.RobolectricBaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AuthTokenRepositoryTest : RobolectricBaseTest() {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)
    private val authTokenRepository = AuthTokenRepositoryImpl(sharedPreferences)

    @Test
    fun shouldSaveAuthToken() {
        authTokenRepository.set("my token")
        assertThat(authTokenRepository.get()).isEqualTo("my token")
    }

    @Test
    fun shouldClearAuthToken() {
        authTokenRepository.set("my token")
        authTokenRepository.clear()
        assertThat(authTokenRepository.get()).isNullOrEmpty()
    }

}