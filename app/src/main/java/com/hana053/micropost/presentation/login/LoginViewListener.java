package com.hana053.micropost.presentation.login;

import android.view.View;

import com.hana053.micropost.presentation.core.listeners.FormWatcher;

public interface LoginViewListener extends FormWatcher {

    View.OnClickListener onClickLoginBtn();

}
