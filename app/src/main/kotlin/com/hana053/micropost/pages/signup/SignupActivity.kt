package com.hana053.micropost.pages.signup

import android.os.Bundle
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.getOverridingModule
import com.hana053.micropost.pages.signup.fullname.SignupFullNameFragment
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


class SignupActivity : RxAppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val signupNavigator: SignupNavigator by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initializeInjector()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SignupFullNameFragment())
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

    override fun onBackPressed() {
        signupNavigator.navigateToPrev()
    }

    override fun provideOverridingModule()
        = getOverridingModule(SignupActivity::class.java)

}