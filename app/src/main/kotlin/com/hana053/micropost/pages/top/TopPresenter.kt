package com.hana053.micropost.pages.top

import com.hana053.micropost.pages.Presenter
import com.hana053.micropost.service.Navigator


class TopPresenter(
    override val view: TopView,
    private val navigator: Navigator
) : Presenter<TopView> {

    override fun bind() {
        view.loginClicks
            .bindToLifecycle()
            .subscribe { navigator.navigateToLogin() }

        view.signupClicks
            .bindToLifecycle()
            .subscribe { navigator.navigateToSignup() }
    }

}