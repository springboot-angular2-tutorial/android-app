package com.hana053.micropost.domain

data class User(
    val id: Long,
    val name: String,
    val email: String?,
    override val avatarHash: String,
    val isFollowedByMe: Boolean,
    val userStats: UserStats
) : Avatar

