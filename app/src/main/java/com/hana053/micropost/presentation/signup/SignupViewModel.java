package com.hana053.micropost.presentation.signup;

import android.databinding.ObservableField;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class SignupViewModel {

    public final ObservableField<String> fullName;
    public final ObservableField<String> email;
    public final ObservableField<String> password;

    public SignupViewModel() {
        this(new ObservableField<>(""),
                new ObservableField<>(""),
                new ObservableField<>("")
        );
    }

    @ParcelConstructor
    SignupViewModel(ObservableField<String> fullName,
                    ObservableField<String> email,
                    ObservableField<String> password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

}
