package com.hana053.micropost.ui.pages.top

import android.os.Bundle
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.ui.Navigator
import com.hana053.micropost.ui.NavigatorImpl


class TopActivity : KodeinAppCompatActivity() {

    override fun provideOverridingModule() = Kodein.Module {
        bind<Navigator>() with autoScopedSingleton(androidActivityScope) {
            NavigatorImpl(instance())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_top)
    }

}
