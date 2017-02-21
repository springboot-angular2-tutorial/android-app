package com.hana053.micropost.pages.usershow.detail

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.hana053.micropost.R
import com.hana053.micropost.domain.User
import com.hana053.micropost.pages.ViewWrapper
import com.hana053.micropost.shared.avatar.AvatarView
import com.hana053.micropost.shared.followbtn.FollowBtnView
import com.jakewharton.rxbinding.view.clicks
import kotlinx.android.synthetic.main._user_detail.view.*

class UserShowDetailView(
    override val content: ViewGroup
) : ViewWrapper {

    private val userName = content.tv_user_name
    private val followers = content.tv_followers
    private val followings = content.tv_followings

    // Sub View
    private val followBtnView = FollowBtnView(content.btn_follow)
    private val avatarView = AvatarView(content.img_avatar)

    // Events
    val followersClicks = followers.clicks()
    val followingsClicks = followings.clicks()
    val followClicks = followBtnView.clicks()

    @SuppressLint("SetTextI18n")
    fun render(user: User) {
        followBtnView.render(user)
        avatarView.render(user)
        userName.text = user.name
        followers.text = "${user.userStats.followerCnt} ${context().getString(R.string.followers)}"
        followings.text = "${user.userStats.followingCnt} ${context().getString(R.string.followings)}"
    }

}