package com.hana053.micropost.presentation.micropostnew;

import android.databinding.ObservableField;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Value;

@Parcel
@Value
public class MicropostNewViewModel {

    public final ObservableField<String> content;

    MicropostNewViewModel() {
        this("");
    }

    @ParcelConstructor
    MicropostNewViewModel(String content) {
        this.content = new ObservableField<>(content);
    }

}
