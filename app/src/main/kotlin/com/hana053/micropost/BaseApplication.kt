package com.hana053.micropost

import android.app.Application
import android.support.annotation.VisibleForTesting
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.hana053.micropost.pages.login.LoginActivity
import com.hana053.micropost.pages.login.loginModule
import com.hana053.micropost.pages.main.MainActivity
import com.hana053.micropost.pages.main.mainModule
import com.hana053.micropost.pages.micropostnew.MicropostNewActivity
import com.hana053.micropost.pages.micropostnew.micropostNewModule
import com.hana053.micropost.pages.relateduserlist.RelatedUserListActivity
import com.hana053.micropost.pages.relateduserlist.relatedUserListModule
import com.hana053.micropost.pages.signup.SignupActivity
import com.hana053.micropost.pages.signup.signupModule
import com.hana053.micropost.pages.top.TopActivity
import com.hana053.micropost.pages.top.topModule
import com.hana053.micropost.pages.usershow.UserShowActivity
import com.hana053.micropost.pages.usershow.userShowModule

abstract class BaseApplication : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
    }

    private var _kodein = Kodein {
        import(appModule())
    }

    override val kodein: Kodein
        get() = _kodein

    @VisibleForTesting
    fun setKodein(kodein: Kodein) {
        _kodein = kodein
    }

    private val overridingModules = mutableMapOf<Class<*>, Kodein.Module>(
        Pair(TopActivity::class.java, topModule()),
        Pair(LoginActivity::class.java, loginModule()),
        Pair(MainActivity::class.java, mainModule()),
        Pair(SignupActivity::class.java, signupModule()),
        Pair(UserShowActivity::class.java, userShowModule()),
        Pair(MicropostNewActivity::class.java, micropostNewModule()),
        Pair(RelatedUserListActivity::class.java, relatedUserListModule())
    )

    fun getOverridingModule(clazz: Class<*>) = overridingModules[clazz]!!

}
