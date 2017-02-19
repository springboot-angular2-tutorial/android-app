package com.hana053.micropost.services

import com.hana053.micropost.testing.EmptyResponseBody
import com.hana053.micropost.testing.RobolectricBaseTest
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.robolectric.shadows.ShadowLog
import org.robolectric.shadows.ShadowToast
import retrofit2.Response
import retrofit2.adapter.rxjava.HttpException
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
        assertThat(ShadowToast.getTextOfLatestToast(), `is`("Connection timed out."))
    }

    @Test
    fun shouldHandleConnectException() {
        httpErrorHandler.handleError(ConnectException())
        assertThat(ShadowToast.getTextOfLatestToast(), `is`("Cannot connect to server."))
    }

    @Test
    fun shouldHandle401Error() {
        httpErrorHandler.handleError(createHttpException(401))
        assertThat(ShadowToast.getTextOfLatestToast(), `is`("Please sign in."))
        verify(authService).logout()
    }

    @Test
    fun shouldHandle500Error() {
        httpErrorHandler.handleError(createHttpException(500))
        assertThat(ShadowToast.getTextOfLatestToast(), `is`("Something bad happened."))
    }

    @Test
    fun shouldHandleUnknownError() {
        httpErrorHandler.handleError(RuntimeException())
        assertThat(ShadowToast.getTextOfLatestToast(), `is`("Something bad happened."))
    }

    private fun createHttpException(status: Int): HttpException {
        return HttpException(Response.error<Void>(status, EmptyResponseBody()))
    }

}