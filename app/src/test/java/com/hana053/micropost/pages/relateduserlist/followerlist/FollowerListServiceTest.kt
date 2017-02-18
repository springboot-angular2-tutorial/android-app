package com.hana053.micropost.pages.relateduserlist.followerlist

import com.hana053.micropost.domain.RelatedUser
import com.hana053.micropost.interactors.RelatedUserListInteractor
import com.hana053.micropost.pages.relateduserlist.RelatedUserListAdapter
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestRelatedUser
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import rx.Observable


class FollowerListServiceTest : RobolectricBaseTest() {

    private val relatedUserListInteractor = mock<RelatedUserListInteractor>()
    private val relatedUserListAdapter = RelatedUserListAdapter()
    private val httpErrorHandler = mock<HttpErrorHandler>()
    private val followerListService = FollowerListService(
        interactor = relatedUserListInteractor,
        adapter = relatedUserListAdapter,
        httpErrorHandler = httpErrorHandler,
        context = app
    )

    @Test
    fun shouldListUsers() {
        val userId = 1L
        `when`(relatedUserListInteractor.listFollowers(userId, null))
            .doReturn(Observable.just(listOf(
                TestRelatedUser.copy(relationshipId = 101)
            )))

        followerListService.listUsers(userId).subscribe()
        advance()

        assertThat(relatedUserListAdapter.itemCount, `is`(1))
        assertThat(relatedUserListAdapter.getLastItemId(), `is`(101L))
    }

    @Test
    fun shouldListMoreUsers() {
        relatedUserListAdapter.addAll(0, listOf(
            TestRelatedUser.copy(relationshipId = 103)
        ))
        val userId = 1L
        `when`(relatedUserListInteractor.listFollowers(userId, 103))
            .doReturn(Observable.just(listOf(
                TestRelatedUser.copy(relationshipId = 102),
                TestRelatedUser.copy(relationshipId = 101)
            )))

        followerListService.listUsers(userId).subscribe()
        advance()

        assertThat(relatedUserListAdapter.itemCount, `is`(3))
        assertThat(relatedUserListAdapter.getLastItemId(), `is`(101L))
    }

    @Test
    fun shouldHandleErrorWhenListUsers() {
        val userId = 1L
        val error = Observable.error<List<RelatedUser>>(RuntimeException())
        `when`(relatedUserListInteractor.listFollowers(userId, null)).doReturn(error)

        followerListService.listUsers(userId).subscribe()
        advance()

        verify(httpErrorHandler).handleError(any<RuntimeException>())
    }

}