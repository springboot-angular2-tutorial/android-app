package com.hana053.micropost.pages.micropostnew

import android.os.Bundle
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.content
import com.hana053.micropost.getOverridingModule
import com.hana053.micropost.service.AuthService
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


class MicropostNewActivity : RxAppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val authService: AuthService  by instance()
    private val presenter: MicropostNewPresenter by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_micropost_new)
        initializeInjector()

        if (!authService.auth()) return

        presenter.bind(MicropostNewView(content()))
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

    override fun provideOverridingModule()
        = getOverridingModule(MicropostNewActivity::class.java)
}