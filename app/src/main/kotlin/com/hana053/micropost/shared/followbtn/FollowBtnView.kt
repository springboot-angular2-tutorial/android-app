package com.hana053.micropost.shared.followbtn

import android.widget.Button
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.domain.User
import com.hana053.micropost.service.AuthService
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import rx.Observable


class FollowBtnView(
    private val button: Button
) {

    private val FOLLOW = button.context.getString(R.string.Follow)
    private val UNFOLLOW = button.context.getString(R.string.Unfollow)

    // Props
    val enabled = button.enabled()
    val user: User
        get() = _user

    private lateinit var _user: User

    fun render(user: User) {
        val authService = button.context.appKodein().instance<AuthService>()

        button.visibility().call(!authService.isMyself(user))
        user.isFollowedByMe?.let { if(it) toUnfollow() else toFollow()}
        this._user = user
    }

    fun clicks(): Observable<FollowBtnView> = button.clicks().map { this }

    fun toFollow() {
        button.text = FOLLOW
    }

    fun toUnfollow() {
        button.text = UNFOLLOW
    }

    fun isFollowState() = button.text == FOLLOW

}