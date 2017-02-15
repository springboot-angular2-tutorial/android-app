package com.hana053.micropost.pages.usershow.detail

import com.hana053.micropost.domain.User
import com.hana053.micropost.interactors.UserInteractor
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class UserShowDetailService(
    private val userInteractor: UserInteractor
) {

    fun getUser(userId: Long): Observable<User> {
        return userInteractor.get(userId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

}