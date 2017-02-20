package com.hana053.micropost.shared.avatar

import android.widget.ImageView
import com.hana053.micropost.domain.User
import com.squareup.picasso.Picasso


class AvatarView(
    private val content: ImageView
) {

    fun render(user: User) {
        Picasso.with(content.context)
            .load(user.avatarUrl(96))
            .into(content)
    }

}