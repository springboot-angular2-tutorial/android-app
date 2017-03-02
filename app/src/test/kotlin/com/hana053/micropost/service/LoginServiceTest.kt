package com.hana053.micropost.service

import com.hana053.micropost.interactor.LoginInteractor
import com.hana053.micropost.repository.AuthTokenRepository
import com.hana053.micropost.testing.EmptyResponseBody
import com.hana053.micropost.testing.RobolectricBaseTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import org.robolectric.shadows.ShadowToast
import retrofit2.HttpException
import retrofit2.Response
import rx.Observable


class LoginServiceTest : RobolectricBaseTest() {

    private val loginInteractor = mock<LoginInteractor>()
    private val authTokenRepository = mock<AuthTokenRepository>()
    private val httpErrorHandler = mock<HttpErrorHandler>()
    private val loginService = LoginServiceImpl(
        loginInteractor = loginInteractor,
        authTokenRepository = authTokenRepository,
        httpErrorHandler = httpErrorHandler,
        context = app
    )

    @Test
    fun shouldLogin() {
        `when`(loginInteractor.login(any()))
            .doReturn(Observable.just(LoginInteractor.LoginResponse("my token")))

        loginService.login("test@test.com", "secret").subscribe()
        advance()

        verify(authTokenRepository).set("my token")
    }

    @Test
    fun shouldNotLoginWhenEmailOrPasswordIsWrong() {
        val error = Observable.error<LoginInteractor.LoginResponse>(HttpException(Response.error<Void>(401, EmptyResponseBody())))
        `when`(loginInteractor.login(any())).doReturn(error)

        loginService.login("test@test.com", "secret").subscribe()
        advance()

        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Email or Password is wrong.")
    }

    @Test
    fun shouldNotLoginWhenSomethingBadHappened() {
        val error = Observable.error<LoginInteractor.LoginResponse>(RuntimeException())
        `when`(loginInteractor.login(any())).doReturn(error)

        loginService.login("test@test.com", "secret").subscribe()
        advance()

        verify(httpErrorHandler).handleError(any<RuntimeException>())
    }

}