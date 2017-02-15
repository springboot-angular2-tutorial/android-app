package com.hana053.micropost.activity

interface Navigator {

    fun navigateToMain()
    fun navigateToLogin()
    fun navigateToSignup()
    fun navigateToUserShow(userId: Long)
    fun navigateToFollowerList(userId: Long)
    fun navigateToFollowingList(userId: Long)
    fun navigateToMicropostNew()

}
