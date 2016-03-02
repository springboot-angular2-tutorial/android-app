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
    public final boolean isMyself;
    public final UserStats userStats;

    @ParcelConstructor
    public User(long id, String name, String email, boolean isMyself, UserStats userStats) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isMyself = isMyself;
        this.userStats = userStats;
    }

    public User(long id, String name, String email) {
        this(id, name, email, false, new UserStats());
    }

}
