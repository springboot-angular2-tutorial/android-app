package com.hana053.micropost.domain;


import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.io.Serializable;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
@Parcel
public final class Micropost implements Serializable {

    static final long serialVersionUID = 1L;

    public final long id;
    public final String content;
    public final long createdAt;
    public final User user;

    @ParcelConstructor
    public Micropost(long id, String content, long createdAt, User user) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }
}
