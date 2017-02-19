package com.hana053.micropost.pages.top

import com.hana053.micropost.service.Navigator
import com.trello.rxlifecycle.kotlin.bindToLifecycle


class TopPresenter(
    private val navigator: Navigator
) {

    fun bind(view: TopView) {
        view.loginClicks
            .bindToLifecycle(view.content)
            .subscribe { navigator.navigateToLogin() }

        view.signupClicks
            .bindToLifecycle(view.content)
            .subscribe { navigator.navigateToSignup() }
    }

}