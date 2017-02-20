package com.hana053.micropost.domain


data class UserStats(
    val micropostCnt: Int,
    val followingCnt: Int,
    val followerCnt: Int
)