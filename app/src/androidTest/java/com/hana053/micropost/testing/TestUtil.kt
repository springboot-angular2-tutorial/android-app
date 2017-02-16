package com.hana053.micropost.testing

import android.support.test.InstrumentationRegistry
import com.hana053.micropost.Application
import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.domain.User
import com.hana053.micropost.domain.UserStats

val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application

val TestUserStats = UserStats(1, 2, 3)
val TestUser = User(1, "John Doe", "test@test.com", "hash", false, TestUserStats)
val TestMicropost = Micropost(1, "content", 0, TestUser)
