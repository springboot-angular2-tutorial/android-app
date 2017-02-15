package com.hana053.micropost.shared.avatar

import android.widget.ImageView
import com.hana053.micropost.domain.User
import com.squareup.picasso.Picasso


class AvatarView(
    content: ImageView,
    user: User
) {

    init {
        Picasso.with(content.context)
            .load(user.avatarUrl(96))
            .into(content)
    }

}