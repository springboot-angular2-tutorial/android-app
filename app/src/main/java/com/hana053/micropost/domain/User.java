package com.hana053.micropost.domain;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.io.Serializable;

import lombok.Data;

@Parcel
@Data
public class User implements Serializable {

    static final long serialVersionUID = 1L;

    public final long id;
    public final String name;
    public final String email;
    public final String avatarHash;
    public final boolean isFollowedByMe;

    @ParcelConstructor
    public User(long id, String name, String email, String avatarHash, boolean isFollowedByMe) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarHash = avatarHash;
        this.isFollowedByMe = isFollowedByMe;
    }

    public User(long id, String name, String email, String avatarHash) {
        this(id, name, email, avatarHash, false);
    }

}
