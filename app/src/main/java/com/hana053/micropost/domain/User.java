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
    public final UserStats userStats;

    @ParcelConstructor
    public User(long id, String name, String email, UserStats userStats) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userStats = userStats;
    }

    public User(long id, String name, String email) {
        this(id, name, email, new UserStats());
    }

}
