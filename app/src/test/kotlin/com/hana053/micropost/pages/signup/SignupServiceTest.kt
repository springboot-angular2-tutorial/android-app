package com.hana053.micropost.pages.signup

import com.hana053.micropost.domain.User
import com.hana053.micropost.interactor.UserInteractor
import com.hana053.micropost.service.HttpErrorHandler
import com.hana053.micropost.service.LoginService
import com.hana053.micropost.testing.EmptyResponseBody
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestUser
import com.nhaarman.mockito_kotlin.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import org.robolectric.shadows.ShadowToast
import retrofit2.Response
import retrofit2.adapter.rxjava.HttpException
import rx.Observable


class SignupServiceTest : RobolectricBaseTest() {

    private val userInteractor: UserInteractor = mock()
    private val loginService: LoginService = mock()
    private val signupNavigator: SignupNavigator = mock()
    private val httpErrorHandler: HttpErrorHandler = mock()
    private val signupService = SignupServiceImpl(
        userInteractor = userInteractor,
        loginService = loginService,
        signupNavigator = signupNavigator,
        httpErrorHandler = httpErrorHandler,
        context = app
    )

    private val signupRequest = UserInteractor.SignupRequest(
        name = "John Doe",
        email = "test@test.com",
        password = "secret123"
    )

    @Test
    fun shouldSignup() {
        `when`(userInteractor.create(any()))
            .doReturn(Observable.just(TestUser))

        signupService.signup(signupRequest).subscribe()
        advance()

        verify(userInteractor).create(signupRequest)
        verify(loginService).login("test@test.com", "secret123")
    }

    @Test
    fun shouldHandleEmailAlreadyTakenWhenSignup() {
        val error = Observable.error<User>(HttpException(Response.error<Void>(400, EmptyResponseBody())))
        `when`(userInteractor.create(any())).doReturn(error)

        signupService.signup(signupRequest).subscribe()
        advance()

        assertThat(ShadowToast.getTextOfLatestToast(), `is`("This email is already taken."))
        verify(loginService, never()).login(any(), any())
    }

    @Test
    fun shouldHandleErrorWhenSignup() {
        val error = Observable.error<User>(RuntimeException())
        `when`(userInteractor.create(any())).doReturn(error)

        signupService.signup(signupRequest).subscribe()
        advance()

        verify(httpErrorHandler).handleError(any<RuntimeException>())
        verify(loginService, never()).login(any(), any())
    }
}