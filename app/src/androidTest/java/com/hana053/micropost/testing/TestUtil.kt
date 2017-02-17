package com.hana053.micropost.testing

import android.support.test.InstrumentationRegistry
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.Application
import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.domain.RelatedUser
import com.hana053.micropost.domain.User
import com.hana053.micropost.domain.UserStats
import com.hana053.micropost.services.AuthTokenService
import com.nhaarman.mockito_kotlin.KStubbing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock

val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application

val TestUserStats = UserStats(1, 2, 3)
val TestUser = User(1, "John Doe", "test@test.com", "hash", false, TestUserStats)
val TestMicropost = Micropost(1, "content", 0, TestUser)
val TestRelatedUser = RelatedUser(1, "John Doe", "test@test.com", "hash", false, TestUserStats, 1)

fun Kodein.Builder.fakeAuthTokenAndBind(token: String = "", init: KStubbing<AuthTokenService>.() -> Unit) {
    bind<AuthTokenService>(overrides = true) with instance(mock<AuthTokenService> {
        on { getAuthToken() } doReturn token
        init()
    })
}

fun Kodein.Builder.fakeAuthToken(token: String = "") {
    fakeAuthTokenAndBind(token) { }
}

