package com.hana053.micropost.pages.relateduserlist.followinglist

import com.hana053.micropost.domain.RelatedUser
import com.hana053.micropost.interactor.RelatedUserListInteractor
import com.hana053.micropost.pages.relateduserlist.RelatedUserListAdapter
import com.hana053.micropost.service.HttpErrorHandler
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestRelatedUser
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import rx.Observable


class FollowingListServiceTest : RobolectricBaseTest() {

    private val relatedUserListInteractor = mock<RelatedUserListInteractor>()
    private val relatedUserListAdapter = RelatedUserListAdapter()
    private val httpErrorHandler = mock<HttpErrorHandler>()
    private val followingListService = FollowingListService(
        interactor = relatedUserListInteractor,
        adapter = relatedUserListAdapter,
        httpErrorHandler = httpErrorHandler,
        context = app
    )

    @Test
    fun shouldListUsers() {
        val userId = 1L
        `when`(relatedUserListInteractor.listFollowings(userId, null))
            .doReturn(Observable.just(listOf(
                TestRelatedUser.copy(relationshipId = 101)
            )))

        followingListService.listUsers(userId).subscribe()
        advance()

        assertThat(relatedUserListAdapter.itemCount).isEqualTo(1)
        assertThat(relatedUserListAdapter.getLastItemId()).isEqualTo(101)
    }

    @Test
    fun shouldListMoreUsers() {
        relatedUserListAdapter.addAll(0, listOf(
            TestRelatedUser.copy(relationshipId = 103)
        ))
        val userId = 1L
        `when`(relatedUserListInteractor.listFollowings(userId, 103))
            .doReturn(Observable.just(listOf(
                TestRelatedUser.copy(relationshipId = 102),
                TestRelatedUser.copy(relationshipId = 101)
            )))

        followingListService.listUsers(userId).subscribe()
        advance()

        assertThat(relatedUserListAdapter.itemCount).isEqualTo(3)
        assertThat(relatedUserListAdapter.getLastItemId()).isEqualTo(101)
    }

    @Test
    fun shouldHandleErrorWhenListUsers() {
        val userId = 1L
        val error = Observable.error<List<RelatedUser>>(RuntimeException())
        `when`(relatedUserListInteractor.listFollowings(userId, null)).doReturn(error)

        followingListService.listUsers(userId).subscribe()
        advance()

        verify(httpErrorHandler).handleError(any<RuntimeException>())
    }
}