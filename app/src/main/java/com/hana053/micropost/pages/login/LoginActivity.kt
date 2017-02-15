package com.hana053.micropost.pages.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.getOverridingModule
import rx.subscriptions.CompositeSubscription

class LoginActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val presenter: LoginPresenter by instance()
    private val view: LoginView by instance()

    private var subscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeInjector()

        subscription = presenter.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription?.unsubscribe()
        destroyInjector()
    }

    override fun provideOverridingModule()
        = getOverridingModule(LoginActivity::class.java)
}

