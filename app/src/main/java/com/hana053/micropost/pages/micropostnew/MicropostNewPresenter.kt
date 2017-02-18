package com.hana053.micropost.pages.micropostnew

import com.hana053.micropost.withProgressDialog
import com.trello.rxlifecycle.kotlin.bindToLifecycle


class MicropostNewPresenter(
    private val micropostNewService: MicropostNewService,
    private val micropostNewNavigator: MicropostNewNavigator
) {

    fun bind(view: MicropostNewView) {
        val postTextChanges = view.postTextChanges.share()

        postTextChanges
            .bindToLifecycle(view.content)
            .map { it.isNotBlank() }
            .subscribe { view.postBtnEnabled.call(it) }

        view.postBtnClicks
            .bindToLifecycle(view.content)
            .withLatestFrom(postTextChanges, { click, text -> text })
            .flatMap {
                micropostNewService.createPost(it.toString())
                    .withProgressDialog(view.content)
            }
            .subscribe { micropostNewNavigator.finishWithPost() }
    }

}