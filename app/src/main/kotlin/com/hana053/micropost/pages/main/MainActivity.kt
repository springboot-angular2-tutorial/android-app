package com.hana053.micropost.pages.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.getOverridingModule
import com.hana053.micropost.service.AuthService
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


class MainActivity : RxAppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val authService: AuthService by instance()
    private val presenter: MainPresenter by instance()

    companion object {
        val REQUEST_POST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeInjector()

        if (!authService.auth()) return

        presenter.bind()
    }

    override fun onDestroy() {
        super.onDestroy()
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
                authService.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun provideOverridingModule()
        = getOverridingModule(MainActivity::class.java)

}
