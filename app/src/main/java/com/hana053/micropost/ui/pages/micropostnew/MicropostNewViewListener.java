package com.hana053.micropost.ui.pages.micropostnew;

import android.view.View;

import com.hana053.micropost.ui.listeners.FormWatcher;

public interface MicropostNewViewListener extends FormWatcher {

    View.OnClickListener onClickPostBtn();
}
