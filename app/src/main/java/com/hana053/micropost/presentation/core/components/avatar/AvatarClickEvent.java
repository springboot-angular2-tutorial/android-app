package com.hana053.micropost.presentation.core.components.avatar;

import com.hana053.micropost.domain.User;

import lombok.Value;

@Value
public class AvatarClickEvent {

    private final AvatarViewModel avatarViewModel;

    public User getUser() {
        return avatarViewModel.getUser();
    }
}
