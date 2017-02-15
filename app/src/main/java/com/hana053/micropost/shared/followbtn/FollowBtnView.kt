package com.hana053.micropost.shared.followbtn

import android.widget.Button
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.domain.User
import com.hana053.micropost.services.AuthTokenService
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import rx.Observable


class FollowBtnView(
    private val button: Button,
    val user: User
) {

    private val FOLLOW = "FOLLOW"
    private val UNFOLLOW = "UNFOLLOW"

    // Props
    val enabled = button.enabled()

    init {
        val authTokenService = button.context.appKodein().instance<AuthTokenService>()

        button.visibility().call(!authTokenService.isMyself(user))
        if (user.isFollowedByMe) toUnfollow() else toFollow()
    }

    fun clicks(): Observable<FollowBtnView> {
        return button.clicks().map { this }
    }

    fun toFollow() {
        button.text = FOLLOW
    }

    fun toUnfollow() {
        button.text = UNFOLLOW
    }

    fun isFollowState(): Boolean {
        return button.text == FOLLOW
    }
}