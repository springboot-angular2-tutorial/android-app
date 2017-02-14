package com.hana053.micropost.pages.top

import android.os.Bundle
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.activity.Navigator
import com.hana053.micropost.activity.NavigatorImpl
import com.hana053.micropost.content
import rx.subscriptions.CompositeSubscription


class TopActivity : KodeinAppCompatActivity() {

    private val presenter: TopPresenter by instance()

    private var subscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)

        val view = TopView(content())
        subscription = presenter.bind(view)
    }

    override fun onDestroy() {
        subscription?.unsubscribe()
        super.onDestroy()
    }

    override fun provideOverridingModule() = Kodein.Module {
        bind<Navigator>() with autoScopedSingleton(androidActivityScope) {
            NavigatorImpl(instance())
        }
        bind<TopPresenter>() with autoScopedSingleton(androidActivityScope) {
            TopPresenter(instance())
        }
    }

}
