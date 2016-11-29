package com.hana053.micropost.ui.pages.login;

import android.databinding.ObservableField;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class LoginViewModel {

    public final ObservableField<String> email;
    public final ObservableField<String> password;

    LoginViewModel() {
        this(new ObservableField<>(""), new ObservableField<>(""));
    }

    @ParcelConstructor
    LoginViewModel(ObservableField<String> email, ObservableField<String> password) {
        this.email = email;
        this.password = password;
    }

}
