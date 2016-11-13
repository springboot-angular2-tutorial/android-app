package com.hana053.micropost.domain;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Parcel
@Value
public final class RelatedUser extends User implements Serializable {

    static final long serialVersionUID = 1L;

    public final long relationshipId;

    @ParcelConstructor
    public RelatedUser(long id, String name, String email, String avatarHash, long relationshipId, UserStats userStats) {
        super(id, name, email, avatarHash, userStats);
        this.relationshipId = relationshipId;
    }

    public RelatedUser(long id, String name, String email, String avatarHash, long relationshipId) {
        this(id, name, email, avatarHash, relationshipId, null);
    }

}
