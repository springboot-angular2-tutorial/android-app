package com.hana053.micropost.presentation.core.services;

import android.content.Context;
import android.content.Intent;

import com.hana053.micropost.interactors.LoginInteractor;
import com.hana053.micropost.presentation.top.TopActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class LoginService {

    private final LoginInteractor loginInteractor;
    private final AuthTokenService authTokenService;
    private final Context context;

    @Inject
    LoginService(LoginInteractor loginInteractor,
                 AuthTokenService authTokenService,
                 Context context) {
        this.loginInteractor = loginInteractor;
        this.authTokenService = authTokenService;
        this.context = context;
    }

    public Observable<Void> login(String email, String password) {
        return loginInteractor
                .login(new LoginInteractor.LoginRequest(email, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(response -> authTokenService.setAuthToken(response.getToken()))
                .flatMap(loginResponse -> Observable.just(null));
    }

    public void logout() {
        authTokenService.clearAuthToken();
        Intent intent = new Intent(context, TopActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public boolean ensureAuthenticated() {
        if (authTokenService.getAuthToken() == null) {
            logout();
            return false;
        }
        return true;
    }
}
