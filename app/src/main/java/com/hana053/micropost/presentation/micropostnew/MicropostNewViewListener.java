package com.hana053.micropost.presentation.micropostnew;

import android.view.View;

import com.hana053.micropost.presentation.core.listeners.FormWatcher;

public interface MicropostNewViewListener extends FormWatcher {

    View.OnClickListener onClickPostBtn();
}
