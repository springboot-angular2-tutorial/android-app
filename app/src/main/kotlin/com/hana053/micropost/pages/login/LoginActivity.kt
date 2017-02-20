package com.hana053.micropost.pages.login

import android.os.Bundle
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.getOverridingModule
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

class LoginActivity : RxAppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val presenter: LoginPresenter by instance()
    private val view: LoginView by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeInjector()

        presenter.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

    override fun provideOverridingModule()
        = getOverridingModule(LoginActivity::class.java)
}

