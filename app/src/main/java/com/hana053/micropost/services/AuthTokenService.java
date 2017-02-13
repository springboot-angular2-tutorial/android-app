package com.hana053.micropost.services;

public interface AuthTokenService {

    String getAuthToken();

    void setAuthToken(String authToken);

    void clearAuthToken();

}
