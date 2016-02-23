package com.hana053.micropost.presentation.signup;

import android.view.View;

import com.hana053.micropost.presentation.core.listeners.FormWatcher;

public interface SignupViewListener extends FormWatcher {

    View.OnClickListener onClickNextBtn();
}
