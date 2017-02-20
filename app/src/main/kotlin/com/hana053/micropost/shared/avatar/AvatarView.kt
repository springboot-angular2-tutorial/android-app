package com.hana053.micropost.shared.avatar

import android.widget.ImageView
import com.hana053.micropost.domain.User
import com.squareup.picasso.Picasso


class AvatarView(
    private val content: ImageView,
    private val size: Int = 96

) {

    fun render(user: User) {
        Picasso.with(content.context)
            .load(user.avatarUrl(size))
            .into(content)
    }

}