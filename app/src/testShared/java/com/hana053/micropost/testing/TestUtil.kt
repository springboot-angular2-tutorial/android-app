package com.hana053.micropost.testing

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.domain.RelatedUser
import com.hana053.micropost.domain.User
import com.hana053.micropost.domain.UserStats
import com.hana053.micropost.services.AuthTokenRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock

val TestUserStats = UserStats(1, 2, 3)
val TestUser = User(1, "John Doe", "test@test.com", "hash", false, TestUserStats)
val TestMicropost = Micropost(1, "content", 0, TestUser)
val TestRelatedUser = RelatedUser(1, "John Doe", "test@test.com", "hash", false, TestUserStats, 1)

// This token has userId = 1.
val jwtForUserId1 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNDc5NDUwNDY0fQ.Dy33qbg6EnP1bL2DmItMNGDEunrYP7-rzf586wxb2D-wW8WCsFrKdCeCU_ZHq_A7-kg_LxBykyaoG_26z-k9uA"

fun Kodein.Builder.fakeAuthToken(token: String = jwtForUserId1) {
    bind<AuthTokenRepository>(overrides = true) with instance(mock<AuthTokenRepository> {
        on { getAuthToken() } doReturn token
    })
}

