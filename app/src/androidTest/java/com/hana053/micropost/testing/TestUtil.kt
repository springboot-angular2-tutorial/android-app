package com.hana053.micropost.testing

import android.support.test.InstrumentationRegistry
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.Application
import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.domain.User
import com.hana053.micropost.domain.UserStats
import com.hana053.micropost.services.LoginService
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock

val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application

val TestUserStats = UserStats(1, 2, 3)
val TestUser = User(1, "John Doe", "test@test.com", "hash", false, TestUserStats)
val TestMicropost = Micropost(1, "content", 0, TestUser)

fun Kodein.Builder.fakeAuth() {
    bind<LoginService>(overrides = true) with instance(mock<LoginService> {
        on { auth() } doReturn true
    })
}
