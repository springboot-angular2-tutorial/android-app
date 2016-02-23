package com.hana053.micropost.presentation.core.components.avatar;

import android.view.View;

import org.greenrobot.eventbus.EventBus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AvatarViewListener {

    private final EventBus eventBus = EventBus.getDefault();

    private final AvatarViewModel avatarViewModel;

    public View.OnClickListener onClick() {
        return v -> eventBus.post(new AvatarClickEvent(avatarViewModel));
    }
}
