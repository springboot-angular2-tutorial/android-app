package com.hana053.micropost.shared.followbtn

import android.content.Context
import android.widget.Button
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.interactors.RelationshipInteractor
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestUser
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
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
        overrideAppBindings {
            bind<Context>() with instance(app)
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
        assertThat(view.isFollowState(), `is`(true))

        followBtnService.handleFollowBtnClicks(view).subscribe()
        advance()

        assertThat(view.isFollowState(), `is`(false))
    }

    @Test
    fun shouldUnfollow() {
        val userId = 1L
        `when`(relationshipInteractor.unfollow(userId))
            .doReturn(Observable.just<Void>(null))

        val view = FollowBtnView(Button(app))
        view.render(TestUser.copy(id = userId, isFollowedByMe = true))

        // just confirm that Unfollow is shown
        assertThat(view.isFollowState(), `is`(false))

        followBtnService.handleFollowBtnClicks(view).subscribe()
        advance()

        assertThat(view.isFollowState(), `is`(true))
    }
}