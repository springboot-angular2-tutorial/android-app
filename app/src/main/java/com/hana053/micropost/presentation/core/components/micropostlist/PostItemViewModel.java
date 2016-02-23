package com.hana053.micropost.presentation.core.components.micropostlist;

import com.hana053.micropost.domain.Micropost;

import lombok.Value;

@Value
public class PostItemViewModel {

    private final Micropost micropost;

    public String getUserName() {
        return micropost.getUser().getName();
    }

    public String getContent() {
        return micropost.getContent();
    }

    public long getCreatedAt() {
        return micropost.getCreatedAt();
    }

}
