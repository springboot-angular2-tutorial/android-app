package com.hana053.micropost.services;

import com.hana053.micropost.domain.User;

public interface AuthTokenService {

    String getAuthToken();

    void setAuthToken(String authToken);

    void clearAuthToken();

    boolean isMyself(User user);

}
