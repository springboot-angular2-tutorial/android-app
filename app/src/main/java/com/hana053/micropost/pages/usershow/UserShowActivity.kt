package com.hana053.micropost.pages.usershow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.getOverridingModule
import com.hana053.micropost.pages.usershow.detail.UserShowDetailPresenter
import com.hana053.micropost.pages.usershow.detail.UserShowDetailView
import com.hana053.micropost.pages.usershow.posts.UserShowPostsPresenter
import com.hana053.micropost.pages.usershow.posts.UserShowPostsView
import com.hana053.micropost.services.LoginService
import rx.subscriptions.CompositeSubscription

class UserShowActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val loginService: LoginService by instance()

    private val detailView: UserShowDetailView by instance()
    private val detailPresenter: UserShowDetailPresenter by instance()

    private val postsView: UserShowPostsView by instance()
    private val postsPresenter: UserShowPostsPresenter by instance()

    private var detailSubscription: CompositeSubscription? = null
    private var postsSubscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_show)
        initializeInjector()

        if (!loginService.auth()) return

        val userId = intent.extras.getLong(KEY_USER_ID)

        detailSubscription = detailPresenter.bind(detailView, userId)
        postsSubscription = postsPresenter.bind(postsView, userId)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        detailSubscription?.unsubscribe()
        postsSubscription?.unsubscribe()
        destroyInjector()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun provideOverridingModule()
        = getOverridingModule(UserShowActivity::class.java)

    companion object {
        val KEY_USER_ID = "KEY_USER_ID"
    }

}