package com.hana053.micropost.pages.usershow.detail

import com.hana053.micropost.domain.User
import com.hana053.micropost.interactors.UserInteractor
import com.hana053.micropost.services.HttpErrorHandler
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class UserShowDetailService(
    private val userInteractor: UserInteractor,
    private val httpErrorHandler: HttpErrorHandler
) {

    fun getUser(userId: Long): Observable<User> {
        return userInteractor.get(userId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { httpErrorHandler.handleError(it) }
            .onErrorResumeNext { Observable.empty() }
    }

}