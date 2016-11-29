package com.hana053.micropost.presentation.micropostnew;

import android.databinding.ObservableField;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class MicropostNewViewModel {

    public final ObservableField<String> content;

    MicropostNewViewModel() {
        this(new ObservableField<>(""));
    }

    @ParcelConstructor
    MicropostNewViewModel(ObservableField<String> content) {
        this.content = content;
    }

}
