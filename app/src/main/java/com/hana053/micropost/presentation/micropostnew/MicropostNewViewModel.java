package com.hana053.micropost.presentation.micropostnew;

import android.databinding.ObservableField;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Value;

@Parcel
@Value
public class MicropostNewViewModel {

    public final ObservableField<String> content;

    public MicropostNewViewModel() {
        this("");
    }

    @ParcelConstructor
    public MicropostNewViewModel(String content) {
        this.content = new ObservableField<>(content);
    }

}
