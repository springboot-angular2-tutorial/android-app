package com.hana053.micropost.ui.pages.signup;

import android.view.View;

import com.hana053.micropost.ui.listeners.FormWatcher;

public interface SignupViewListener extends FormWatcher {

    View.OnClickListener onClickNextBtn();
}
