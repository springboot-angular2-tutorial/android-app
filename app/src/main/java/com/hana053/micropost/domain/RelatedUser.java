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
    public RelatedUser(long id, String name, String email, UserStats userStats, long relationshipId) {
        super(id, name, email, userStats);
        this.relationshipId = relationshipId;
    }
}
