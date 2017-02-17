package com.hana053.micropost.pages.signup.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.SupportFragmentInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.content
import com.trello.rxlifecycle.components.support.RxFragment


class SignupPasswordFragment : RxFragment(), SupportFragmentInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val presenter: SignupPasswordPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeInjector()

        presenter.bind(SignupPasswordView(activity.content()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        destroyInjector()
    }

}