package com.hana053.micropost.ui.pages.signup;

import com.hana053.micropost.services.HttpErrorHandler;
import com.hana053.micropost.ui.Navigator;
import com.hana053.micropost.ui.ProgressBarHandler;

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
