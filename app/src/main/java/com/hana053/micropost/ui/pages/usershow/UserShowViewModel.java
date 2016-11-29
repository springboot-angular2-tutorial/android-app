package com.hana053.micropost.ui.pages.usershow;

import com.hana053.micropost.domain.User;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Value;

@Parcel
@Value
public class UserShowViewModel {

    public final User user;
    public final boolean isMyself;

    @ParcelConstructor
    UserShowViewModel(User user, boolean isMyself) {
        this.user = user;
        this.isMyself = isMyself;
    }
}
