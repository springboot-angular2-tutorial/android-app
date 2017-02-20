package com.hana053.micropost.pages.main

import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.service.HttpErrorHandler
import com.hana053.micropost.shared.posts.PostListAdapter
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestMicropost
import com.hana053.myapp.interactors.FeedInteractor
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import rx.Observable


class MainServiceTest : RobolectricBaseTest() {

    private val feedInteractor = mock<FeedInteractor>()
    private val httpErrorHandler = mock<HttpErrorHandler>()
    private val postListAdapter = PostListAdapter()
    private val mainService = MainService(
        feedInteractor = feedInteractor,
        httpErrorHandler = httpErrorHandler,
        postListAdapter = postListAdapter
    )

    @Test
    fun shouldLoadNextFeed() {
        `when`(feedInteractor.loadNextFeed(null))
            .doReturn(Observable.just(listOf(TestMicropost)))

        mainService.loadNextFeed().subscribe()
        advance()

        assertThat(postListAdapter.itemCount, `is`(1))
        assertThat(postListAdapter.getFirstItemId(), `is`(TestMicropost.id))
    }

    @Test
    fun shouldLoadMoreNextFeed() {
        postListAdapter.addAll(0, listOf(TestMicropost.copy(id = 1)))
        `when`(feedInteractor.loadNextFeed(1))
            .doReturn(Observable.just(listOf(
                TestMicropost.copy(id = 3),
                TestMicropost.copy(id = 2)
            )))

        mainService.loadNextFeed().subscribe()
        advance()

        assertThat(postListAdapter.itemCount, `is`(3))
        assertThat(postListAdapter.getFirstItemId(), `is`(3L))
        assertThat(postListAdapter.getLastItemId(), `is`(1L))
    }

    @Test
    fun shouldHandleErrorWhenLoadNextFeed() {
        val error = Observable.error<List<Micropost>>(RuntimeException())
        `when`(feedInteractor.loadNextFeed(null)).doReturn(error)

        mainService.loadNextFeed().subscribe()
        advance()

        verify(httpErrorHandler).handleError(any<RuntimeException>())
    }

    @Test
    fun shouldLoadPrevFeed() {
        `when`(feedInteractor.loadPrevFeed(null))
            .doReturn(Observable.just(listOf(TestMicropost)))

        mainService.loadPrevFeed().subscribe()
        advance()

        assertThat(postListAdapter.itemCount, `is`(1))
        assertThat(postListAdapter.getFirstItemId(), `is`(TestMicropost.id))
    }

    @Test
    fun shouldLoadMorePrevFeed() {
        postListAdapter.addAll(0, listOf(TestMicropost.copy(id = 3)))
        `when`(feedInteractor.loadPrevFeed(3))
            .doReturn(Observable.just(listOf(
                TestMicropost.copy(id = 2),
                TestMicropost.copy(id = 1)
            )))

        mainService.loadPrevFeed().subscribe()
        advance()

        assertThat(postListAdapter.itemCount, `is`(3))
        assertThat(postListAdapter.getFirstItemId(), `is`(3L))
        assertThat(postListAdapter.getLastItemId(), `is`(1L))
    }

    @Test
    fun shouldHandleErrorWhenLoadPrevFeed() {
        val error = Observable.error<List<Micropost>>(RuntimeException())
        `when`(feedInteractor.loadPrevFeed(null)).doReturn(error)

        mainService.loadPrevFeed().subscribe()
        advance()

        verify(httpErrorHandler).handleError(any<RuntimeException>())
    }
}