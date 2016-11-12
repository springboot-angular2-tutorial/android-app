package com.hana053.micropost.presentation.core.components.avatar;

import com.hana053.micropost.domain.User;

import lombok.Value;

@Value
public class AvatarViewModel {

    private final User user;

    public String getUrl(int size) {
        return "https://secure.gravatar.com/avatar/" + user.getAvatarHash() + "?s=" + size;
    }

}
