package com.hana053.micropost.pages.relateduserlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.getOverridingModule
import com.hana053.micropost.services.LoginService
import rx.subscriptions.CompositeSubscription


class RelatedUserListActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val loginService: LoginService by instance()
    private val view: RelatedUserListView by instance()
    private val presenter: RelatedUserListPresenter by lazy {
        injector.instance<RelatedUserListPresenter>(listType()).value
    }

    private var subscription: CompositeSubscription? = null

    enum class ListType {
        FOLLOWER, FOLLOWING
    }

    companion object {
        val KEY_USER_ID = "KEY_USER_ID"
        val KEY_LIST_TYPE = "KEY_LIST_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_related_user_list)
        initializeInjector()

        if (!loginService.auth()) return

        val userId = intent.extras.getLong(KEY_USER_ID)
        subscription = presenter.bind(view, userId)
    }


    override fun onDestroy() {
        super.onDestroy()
        subscription?.unsubscribe()
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
        = getOverridingModule(RelatedUserListActivity::class.java)

    private fun listType() = intent.extras.getSerializable(KEY_LIST_TYPE) as ListType
}
