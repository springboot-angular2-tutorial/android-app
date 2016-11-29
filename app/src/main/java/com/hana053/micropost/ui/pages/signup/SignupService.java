package com.hana053.micropost.ui.pages.signup;

import com.hana053.micropost.interactors.UserInteractor;

import rx.Observable;

public interface SignupService {

    Observable<Void> signup(UserInteractor.SignupRequest request);

}
