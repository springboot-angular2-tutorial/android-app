package com.hana053.micropost.testing

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.interactors.*
import com.hana053.myapp.interactors.FeedInteractor
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import rx.Observable


fun blockedInteractorModule() = Kodein.Module {

    bind<LoginInteractor>(overrides = true) with instance(mock<LoginInteractor> {
        on { login(any()) } doReturn Observable.error(NetworkNotAllowedException())
    })

    bind<FeedInteractor>(overrides = true) with instance(mock<FeedInteractor> {
        on { loadNextFeed(anyOrNull()) } doReturn Observable.error(NetworkNotAllowedException())
        on { loadPrevFeed(anyOrNull()) } doReturn Observable.error(NetworkNotAllowedException())
    })

    bind<UserInteractor>(overrides = true) with instance(mock<UserInteractor> {
        on { create(any()) } doReturn Observable.error(NetworkNotAllowedException())
        on { get(any()) } doReturn Observable.error(NetworkNotAllowedException())
    })

    bind<RelationshipInteractor>(overrides = true) with instance(mock<RelationshipInteractor> {
        on { follow(any()) } doReturn Observable.error(NetworkNotAllowedException())
        on { unfollow(any()) } doReturn Observable.error(NetworkNotAllowedException())
    })

    bind<UserMicropostInteractor>(overrides = true) with instance(mock<UserMicropostInteractor> {
        on { loadPrevPosts(any(), anyOrNull()) } doReturn Observable.error(NetworkNotAllowedException())
    })

    bind<MicropostInteractor>(overrides = true) with instance(mock<MicropostInteractor> {
        on { create(any()) } doReturn Observable.error(NetworkNotAllowedException())
    })

    bind<RelatedUserListInteractor>(overrides = true) with instance(mock<RelatedUserListInteractor> {
        on { listFollowers(any(), anyOrNull()) } doReturn Observable.error(NetworkNotAllowedException())
        on { listFollowings(any(), anyOrNull()) } doReturn Observable.error(NetworkNotAllowedException())
    })

}

class NetworkNotAllowedException : RuntimeException()
