package com.hana053.micropost.presentation.signup;

import com.hana053.micropost.presentation.core.services.LoginService;
import com.hana053.micropost.interactors.UserInteractor;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignupService {

    private final UserInteractor userInteractor;
    private final LoginService loginService;

    @Inject
    public SignupService(UserInteractor userInteractor, LoginService loginService) {
        this.userInteractor = userInteractor;
        this.loginService = loginService;
    }

    public Observable<Response<Void>> signup(UserInteractor.SignupRequest request) {
        return userInteractor.create(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(user -> loginService.login(request.getEmail(), request.getPassword()));
    }

}
