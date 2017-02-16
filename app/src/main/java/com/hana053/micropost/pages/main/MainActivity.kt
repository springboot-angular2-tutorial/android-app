package com.hana053.micropost.pages.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.getOverridingModule
import com.hana053.micropost.services.LoginService
import rx.subscriptions.CompositeSubscription


class MainActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val loginService: LoginService by instance()
    private val presenter: MainPresenter by instance()
    private val view: MainView by instance()

    private var subscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeInjector()

        if (!loginService.auth()) return

        subscription = presenter.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription?.unsubscribe()
        destroyInjector()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_POST && resultCode == Activity.RESULT_OK) {
            presenter.newPostSubmittedSubject.onNext(null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                loginService.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val REQUEST_POST = 1
    }

    override fun provideOverridingModule()
        = getOverridingModule(MainActivity::class.java)

}
