package com.hana053.micropost.pages.micropostnew

import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.interactors.MicropostInteractor
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.testing.RobolectricBaseTest
import com.hana053.micropost.testing.TestMicropost
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.Mockito.`when`
import rx.Observable


class MicropostNewServiceTest : RobolectricBaseTest() {

    private val micropostInteractor = mock<MicropostInteractor>()
    private val httpErrorHandler = mock<HttpErrorHandler>()
    private val micropostNewService = MicropostNewService(
        micropostInteractor = micropostInteractor,
        httpErrorHandler = httpErrorHandler
    )

    @Test
    fun shouldCreatePost() {
        `when`(micropostInteractor.create(any()))
            .doReturn(Observable.just(TestMicropost))

        micropostNewService.createPost("my post").subscribe()
        advance()

        verify(micropostInteractor)
            .create(MicropostInteractor.MicropostRequest("my post"))
    }

    @Test
    fun shouldHandleErrorWhenCreatePost() {
        val error = Observable.error<Micropost>(RuntimeException())
        `when`(micropostInteractor.create(any())).doReturn(error)

        micropostNewService.createPost("my post").subscribe()
        advance()

        verify(httpErrorHandler).handleError(any<RuntimeException>())
    }
}