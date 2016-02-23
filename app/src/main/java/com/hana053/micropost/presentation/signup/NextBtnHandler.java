package com.hana053.micropost.presentation.signup;

import android.databinding.ObservableBoolean;

public class NextBtnHandler {

    public final ObservableBoolean enabled = new ObservableBoolean(false);

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

}
