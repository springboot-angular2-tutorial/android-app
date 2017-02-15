package com.hana053.micropost.pages.usershow.detail

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.hana053.micropost.domain.User
import com.hana053.micropost.shared.avatar.AvatarView
import com.hana053.micropost.shared.followbtn.FollowBtnView
import com.jakewharton.rxbinding.view.clicks
import kotlinx.android.synthetic.main.user_detail.view.*

class UserShowDetailView(
    val content: ViewGroup,
    user: User
) {

    private val userName = content.userName
    private val followerCnt = content.followerCnt
    private val followingCnt = content.followingCnt
    private val followers = content.followers
    private val followings = content.followings

    // Sub View
    val followBtnView = FollowBtnView(content.followBtn, user)

    // Events
    val followersClicks = followers.clicks()
    val followingsClicks = followings.clicks()
    val followClicks = followBtnView.clicks()

    init {
        AvatarView(content.avatar, user)
        renderUser(user)
    }

    @SuppressLint("SetTextI18n")
    fun renderUser(user: User) {
        userName.text = user.name
        followingCnt.text = "${user.userStats.followingCnt} "
        followerCnt.text = "${user.userStats.followerCnt} "
    }

}