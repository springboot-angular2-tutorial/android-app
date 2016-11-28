package com.hana053.micropost.presentation.core.services;

import com.hana053.micropost.domain.User;

public interface Navigator {

    void navigateToUserShow(User user);

    void navigateToMain();

    void navigateToSignup();

    void navigateToLogin();

    void navigateToFollowings(long userId);

    void navigateToFollowers(long userId);

}
