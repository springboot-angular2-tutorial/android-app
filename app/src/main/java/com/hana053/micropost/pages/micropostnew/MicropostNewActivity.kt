package com.hana053.micropost.pages.micropostnew

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.content
import com.hana053.micropost.getOverridingModule
import com.hana053.micropost.services.LoginService
import rx.subscriptions.CompositeSubscription


class MicropostNewActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val loginService: LoginService  by instance()
    private val presenter: MicropostNewPresenter by instance()

    private var subscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_micropost_new)
        initializeInjector()

        if (!loginService.auth()) return

        subscription = presenter.bind(MicropostNewView(content()))
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription?.unsubscribe()
        destroyInjector()
    }

    override fun provideOverridingModule()
        = getOverridingModule(MicropostNewActivity::class.java)
}