package com.hana053.micropost.pages.top

import com.hana053.micropost.activity.Navigator
import rx.subscriptions.CompositeSubscription


class TopPresenter(
    private val navigator: Navigator
) {

    fun bind(view: TopView): CompositeSubscription {
        val subscriptions = CompositeSubscription()

        view.loginClicks
            .subscribe { navigator.navigateToLogin() }

//        view.signupClicks
//            .subscribe { navigator.navigateToSignup() }

        return subscriptions
    }

}