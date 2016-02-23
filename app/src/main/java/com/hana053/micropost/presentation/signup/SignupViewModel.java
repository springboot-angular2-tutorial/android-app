package com.hana053.micropost.presentation.signup;

import android.databinding.ObservableField;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Value;

@Parcel
@Value
public class SignupViewModel {

    public final ObservableField<String> fullName;
    public final ObservableField<String> email;
    public final ObservableField<String> password;

    public SignupViewModel() {
        this("", "", "");
    }

    @ParcelConstructor
    public SignupViewModel(String fullName, String email, String password) {
        this.fullName = new ObservableField<>(fullName);
        this.email = new ObservableField<>(email);
        this.password = new ObservableField<>(password);
    }

}
