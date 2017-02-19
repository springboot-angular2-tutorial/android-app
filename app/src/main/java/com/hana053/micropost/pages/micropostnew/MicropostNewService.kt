package com.hana053.micropost.pages.micropostnew

import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.interactor.MicropostInteractor
import com.hana053.micropost.service.HttpErrorHandler
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MicropostNewService(
    private val micropostInteractor: MicropostInteractor,
    private val httpErrorHandler: HttpErrorHandler
) {

    fun createPost(content: String): Observable<Micropost> {
        return micropostInteractor
            .create(MicropostInteractor.MicropostRequest(content))
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { httpErrorHandler.handleError(it) }
            .onErrorResumeNext(Observable.empty())
    }
}