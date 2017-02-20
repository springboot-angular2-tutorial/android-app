package com.hana053.micropost.pages.usershow.detail

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.hana053.micropost.domain.User
import com.hana053.micropost.pages.ViewWrapper
import com.hana053.micropost.shared.avatar.AvatarView
import com.hana053.micropost.shared.followbtn.FollowBtnView
import com.jakewharton.rxbinding.view.clicks
import kotlinx.android.synthetic.main._user_detail.view.*

class UserShowDetailView(
    override val content: ViewGroup
) : ViewWrapper {

    private val userName = content.userName
    private val followerCnt = content.followerCnt
    private val followingCnt = content.followingCnt
    private val followers = content.followers
    private val followings = content.followings

    // Sub View
    private val followBtnView = FollowBtnView(content.followBtn)
    private val avatarView = AvatarView(content.avatar)

    // Events
    val followersClicks = followers.clicks()
    val followingsClicks = followings.clicks()
    val followClicks = followBtnView.clicks()

    @SuppressLint("SetTextI18n")
    fun render(user: User) {
        followBtnView.render(user)
        avatarView.render(user)
        userName.text = user.name
        followingCnt.text = "${user.userStats.followingCnt} "
        followerCnt.text = "${user.userStats.followerCnt} "
    }

}