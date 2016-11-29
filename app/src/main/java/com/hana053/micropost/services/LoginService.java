package com.hana053.micropost.services;

import rx.Observable;

public interface LoginService {

    Observable<Void> login(String email, String password);

    void logout();

    boolean ensureAuthenticated();

}
