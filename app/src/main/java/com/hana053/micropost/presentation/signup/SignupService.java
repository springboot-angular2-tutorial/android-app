package com.hana053.micropost.presentation.signup;

import com.hana053.micropost.interactors.UserInteractor;

import rx.Observable;

public interface SignupService {

    Observable<Void> signup(UserInteractor.SignupRequest request);

}
