package com.hana053.micropost.service

import com.hana053.micropost.testing.EmptyResponseBody
import com.hana053.micropost.testing.RobolectricBaseTest
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.robolectric.shadows.ShadowLog
import org.robolectric.shadows.ShadowToast
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException


class HttpErrorHandlerTest : RobolectricBaseTest() {

    private val authService = mock<AuthService>()
    private val httpErrorHandler = HttpErrorHandlerImpl(authService, app)

    @Before
    fun suppressLog() {
        ShadowLog.stream = null
    }

    @Test
    fun shouldHandleSocketTimeoutException() {
        httpErrorHandler.handleError(SocketTimeoutException())
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Connection timed out.")
    }

    @Test
    fun shouldHandleConnectException() {
        httpErrorHandler.handleError(ConnectException())
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Cannot connect to server.")
    }

    @Test
    fun shouldHandle401Error() {
        httpErrorHandler.handleError(createHttpException(401))
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Please sign in.")
        verify(authService).logout()
    }

    @Test
    fun shouldHandle500Error() {
        httpErrorHandler.handleError(createHttpException(500))
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Something bad happened.")
    }

    @Test
    fun shouldHandleUnknownError() {
        httpErrorHandler.handleError(RuntimeException())
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Something bad happened.")
    }

    private fun createHttpException(status: Int): HttpException {
        return HttpException(Response.error<Void>(status, EmptyResponseBody()))
    }

}