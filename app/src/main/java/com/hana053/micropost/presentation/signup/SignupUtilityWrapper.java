package com.hana053.micropost.presentation.signup;


import com.hana053.micropost.presentation.core.services.HttpErrorHandler;
import com.hana053.micropost.presentation.core.services.Navigator;
import com.hana053.micropost.presentation.core.services.ProgressBarHandler;

import javax.inject.Inject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import rx.subscriptions.CompositeSubscription;

@NoArgsConstructor
@Getter
public class SignupUtilityWrapper {

    @Inject
    SignupService signupService;

    @Inject
    ProgressBarHandler progressBarHandler;

    @Inject
    HttpErrorHandler httpErrorHandler;

    @Inject
    Navigator navigator;

    @Inject
    SignupCtrl signupCtrl;

    @Inject
    CompositeSubscription subscriptions;
}
