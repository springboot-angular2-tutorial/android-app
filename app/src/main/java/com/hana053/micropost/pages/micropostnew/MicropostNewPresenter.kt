package com.hana053.micropost.pages.micropostnew

import com.hana053.micropost.plusAssign
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.withProgressDialog
import rx.subscriptions.CompositeSubscription


class MicropostNewPresenter(
    private val micropostNewService: MicropostNewService,
    private val micropostNewNavigator: MicropostNewNavigator,
    private val httpErrorHandler: HttpErrorHandler
) {

    fun bind(view: MicropostNewView): CompositeSubscription {
        val subscriptions = CompositeSubscription()

        val postTextChanges = view.postTextChanges.share()

        subscriptions += postTextChanges
            .map { it.isNotBlank() }
            .subscribe { view.postBtnEnabled.call(it) }

        subscriptions += view.postBtnClicks
            .withLatestFrom(postTextChanges, { click, text -> text })
            .flatMap {
                micropostNewService.createPost(it.toString())
                    .withProgressDialog(view.content)
            }
            .subscribe({
                micropostNewNavigator.finishWithPost()
            }, {
                httpErrorHandler.handleError(it)
            })

        return subscriptions
    }

}