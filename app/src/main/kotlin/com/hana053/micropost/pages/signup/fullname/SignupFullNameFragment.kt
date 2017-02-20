package com.hana053.micropost.pages.signup.fullname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.SupportFragmentInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.trello.rxlifecycle.components.support.RxFragment


class SignupFullNameFragment : RxFragment(), SupportFragmentInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val presenter: SignupFullNamePresenter by instance()
    private val view: SignupFullNameView by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup_full_name, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeInjector()

        presenter.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

}