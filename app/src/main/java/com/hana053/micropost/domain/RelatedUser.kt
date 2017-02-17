package com.hana053.micropost.domain


data class RelatedUser(
    val id: Long,
    val name: String,
    val email: String?,
    override val avatarHash: String,
    val isFollowedByMe: Boolean,
    val userStats: UserStats,
    val relationshipId: Long
) : Avatar {
    fun toUser(): User {
        return User(id, name, email, avatarHash, isFollowedByMe, userStats)
    }
}