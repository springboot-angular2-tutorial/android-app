package com.hana053.micropost.presentation.login;

import android.databinding.ObservableField;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Value;

@Parcel
@Value
public class LoginViewModel {

    public final ObservableField<String> email;
    public final ObservableField<String> password;

    LoginViewModel() {
        this("", "");
    }

    @ParcelConstructor
    LoginViewModel(String email, String password) {
        this.email = new ObservableField<>(email);
        this.password = new ObservableField<>(password);
    }

}
