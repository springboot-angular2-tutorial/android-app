package com.hana053.micropost.presentation.usershow;

import com.hana053.micropost.domain.User;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Value;

@Parcel
@Value
public class UserShowViewModel {

    public final User user;

    @ParcelConstructor
    public UserShowViewModel(User user) {
        this.user = user;
    }
}
