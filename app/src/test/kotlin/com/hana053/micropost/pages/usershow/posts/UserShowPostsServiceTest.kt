package com.hana053.micropost.pages.usershow.posts

import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.interactor.UserMicropostInteractor
import com.hana053.micropost.service.HttpErrorHandler
import com.hana053.micropost.shared.posts.PostListAdapter
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestMicropost
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import rx.Observable


class UserShowPostsServiceTest : RobolectricBaseTest() {

    private val postListAdapter = PostListAdapter()
    private val userMicropostInteractor: UserMicropostInteractor = mock()
    private val httpErrorHandler: HttpErrorHandler = mock()
    private val userShowPostsService = UserShowPostsService(
        postListAdapter = postListAdapter,
        userMicropostInteractor = userMicropostInteractor,
        httpErrorHandler = httpErrorHandler
    )

    @Test
    fun shouldLoadPosts() {
        val userId = 100L
        `when`(userMicropostInteractor.loadPrevPosts(userId, null))
            .doReturn(Observable.just(listOf(
                TestMicropost.copy(id = 1)
            )))

        userShowPostsService.loadPosts(userId).subscribe()
        advance()

        assertThat(postListAdapter.itemCount).isEqualTo(1)
        assertThat(postListAdapter.getFirstItemId()).isEqualTo(1)
    }

    @Test
    fun shouldLoadMorePosts() {
        val userId = 100L
        postListAdapter.addAll(0, listOf(
            TestMicropost.copy(id = 3)
        ))
        `when`(userMicropostInteractor.loadPrevPosts(userId, 3))
            .doReturn(Observable.just(listOf(
                TestMicropost.copy(id = 2),
                TestMicropost.copy(id = 1)
            )))

        userShowPostsService.loadPosts(userId).subscribe()
        advance()

        assertThat(postListAdapter.itemCount).isEqualTo(3)
        assertThat(postListAdapter.getFirstItemId()).isEqualTo(3)
        assertThat(postListAdapter.getLastItemId()).isEqualTo(1)
    }

    @Test
    fun shouldHandleErrorWhenLoadPosts() {
        val userId = 100L
        val error = Observable.error<List<Micropost>>(RuntimeException())
        `when`(userMicropostInteractor.loadPrevPosts(userId, null)).doReturn(error)

        userShowPostsService.loadPosts(userId).subscribe()
        advance()

        verify(httpErrorHandler).handleError(any<RuntimeException>())
    }
}
