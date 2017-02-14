package com.hana053.micropost.pages.login

import android.os.Bundle
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.content
import rx.subscriptions.CompositeSubscription

class LoginActivity : KodeinAppCompatActivity() {

    private val presenter: LoginPresenter by instance()

    private var subscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val view = LoginView(content())
        subscription = presenter.bind(view)
    }

    override fun onDestroy() {
        subscription?.unsubscribe()
        super.onDestroy()
    }

    override fun provideOverridingModule() = Kodein.Module {
        bind<LoginPresenter>() with autoScopedSingleton(androidActivityScope) {
            LoginPresenter(instance(), instance(), instance(), instance())
        }
    }
}

