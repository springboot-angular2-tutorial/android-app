package com.hana053.micropost.pages.top

import android.os.Bundle
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.getOverridingModule
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


class TopActivity : RxAppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val presenter: TopPresenter by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        initializeInjector()

        presenter.bind()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

    override fun provideOverridingModule() = getOverridingModule()

}

