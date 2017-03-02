package com.hana053.micropost.pages.usershow.detail

import com.hana053.micropost.domain.User
import com.hana053.micropost.interactor.UserInteractor
import com.hana053.micropost.service.HttpErrorHandler
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestUser
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import rx.Observable


class UserShowDetailServiceTest : RobolectricBaseTest() {

    private val userInteractor: UserInteractor = mock()
    private val httpErrorHandler: HttpErrorHandler = mock()
    private val userShowDetailService = UserShowDetailService(
        userInteractor = userInteractor,
        httpErrorHandler = httpErrorHandler
    )

    @Test
    fun shouldGetUser() {
        val userId = 1L
        `when`(userInteractor.get(userId))
            .doReturn(Observable.just(TestUser))

        var user: User? = null
        userShowDetailService.getUser(userId).subscribe { user = it }
        advance()

        verify(userInteractor).get(userId)
        assertThat(user).isEqualTo(TestUser)
    }

    @Test
    fun shouldHandleErrorWhenGetUser() {
        val error = Observable.error<User>(RuntimeException())
        `when`(userInteractor.get(any())).doReturn(error)

        userShowDetailService.getUser(1).subscribe()
        advance()

        verify(httpErrorHandler).handleError(any<RuntimeException>())
    }

}