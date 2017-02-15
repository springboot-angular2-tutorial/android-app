package com.hana053.micropost.pages.signup.password

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.SupportFragmentInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.content
import rx.subscriptions.CompositeSubscription


class SignupPasswordFragment : Fragment(), SupportFragmentInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val presenter: SignupPasswordPresenter by instance()

    private var subscription: CompositeSubscription? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeInjector()

        subscription = presenter.bind(SignupPasswordView(activity.content()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscription?.unsubscribe()
        destroyInjector()
    }

}