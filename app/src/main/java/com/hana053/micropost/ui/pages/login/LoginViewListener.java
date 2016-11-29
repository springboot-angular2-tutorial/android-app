package com.hana053.micropost.ui.pages.login;

import android.view.View;

import com.hana053.micropost.ui.listeners.FormWatcher;

public interface LoginViewListener extends FormWatcher {

    View.OnClickListener onClickLoginBtn();

}
