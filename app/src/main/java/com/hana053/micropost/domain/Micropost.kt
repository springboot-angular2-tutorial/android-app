package com.hana053.micropost.domain


data class Micropost(
    val id: Long,
    val content: String,
    val createdAt: Long,
    val user: User
)