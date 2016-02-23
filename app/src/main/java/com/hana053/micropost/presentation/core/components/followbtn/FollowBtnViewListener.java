package com.hana053.micropost.presentation.core.components.followbtn;

import android.view.View;

import org.greenrobot.eventbus.EventBus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FollowBtnViewListener {

    private final EventBus eventBus = EventBus.getDefault();

    private final FollowBtnViewModel followBtnViewModel;

    public View.OnClickListener onClick() {
        return v -> eventBus.post(new FollowBtnClickEvent(followBtnViewModel));
    }
}
