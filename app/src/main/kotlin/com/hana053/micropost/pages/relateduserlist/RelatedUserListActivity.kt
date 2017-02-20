package com.hana053.micropost.pages.relateduserlist

import android.os.Bundle
import android.view.MenuItem
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.R
import com.hana053.micropost.getOverridingModule
import com.hana053.micropost.service.AuthService
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


class RelatedUserListActivity : RxAppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private val authService: AuthService by instance()
    private val view: RelatedUserListView by instance()
    private val presenter: RelatedUserListPresenter by instance()
    private val relatedUserListService: RelatedUserListService by instance()

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

        if (!authService.auth()) return

        presenter.bind(view)

        title = relatedUserListService.title()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onDestroy() {
        super.onDestroy()
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

}
