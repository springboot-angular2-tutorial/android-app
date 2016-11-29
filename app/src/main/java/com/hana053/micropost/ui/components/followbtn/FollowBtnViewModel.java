package com.hana053.micropost.ui.components.followbtn;

import android.databinding.ObservableBoolean;

import lombok.Value;

@Value
public class FollowBtnViewModel {

    private final long userId;

    public final ObservableBoolean isEnabled = new ObservableBoolean(true);
    public final ObservableBoolean isFollowedByMe;

    public FollowBtnViewModel(long userId, boolean isFollowedByMe) {
        this.userId = userId;
        this.isFollowedByMe = new ObservableBoolean(isFollowedByMe);
    }
}
