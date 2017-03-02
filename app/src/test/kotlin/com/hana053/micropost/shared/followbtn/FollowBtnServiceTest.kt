package com.hana053.micropost.shared.followbtn

import android.app.Activity
import android.content.Context
import android.widget.Button
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.interactor.RelationshipInteractor
import com.hana053.micropost.service.HttpErrorHandler
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestUser
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.robolectric.Robolectric
import rx.Observable


class FollowBtnServiceTest : RobolectricBaseTest() {

    private val relationshipInteractor = mock<RelationshipInteractor>()
    private val httpErrorHandler = mock<HttpErrorHandler>()
    private val followBtnService = FollowBtnService(
        relationshipInteractor = relationshipInteractor,
        httpErrorHandler = httpErrorHandler
    )

    @Before
    fun setup() {
        val activity = Robolectric.buildActivity(Activity::class.java).create().get()
        overrideAppBindings {
            bind<Context>() with instance(app)
            bind<Activity>() with instance(activity)
        }
    }

    @Test
    fun shouldFollowOrUnfollow() {
        val userId = 1L
        `when`(relationshipInteractor.follow(userId))
            .doReturn(Observable.just<Void>(null))

        val view = FollowBtnView(Button(app))
        view.render(TestUser.copy(id = userId, isFollowedByMe = false))

        // just confirm that Follow is shown
        assertThat(view.isFollowState()).isTrue()

        followBtnService.handleFollowBtnClicks(view).subscribe()
        advance()

        assertThat(view.isFollowState()).isFalse()
    }

    @Test
    fun shouldUnfollow() {
        val userId = 1L
        `when`(relationshipInteractor.unfollow(userId))
            .doReturn(Observable.just<Void>(null))

        val view = FollowBtnView(Button(app))
        view.render(TestUser.copy(id = userId, isFollowedByMe = true))

        // just confirm that Unfollow is shown
        assertThat(view.isFollowState()).isFalse()

        followBtnService.handleFollowBtnClicks(view).subscribe()
        advance()

        assertThat(view.isFollowState()).isTrue()
    }
}