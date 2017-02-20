package com.hana053.micropost.pages.micropostnew

import com.hana053.micropost.pages.Presenter


class MicropostNewPresenter(
    override val view: MicropostNewView,
    private val micropostNewService: MicropostNewService,
    private val micropostNewNavigator: MicropostNewNavigator
) : Presenter<MicropostNewView> {

    override fun bind() {
        val postTextChanges = view.postTextChanges.share()

        postTextChanges
            .bindToLifecycle()
            .map { it.isNotBlank() }
            .subscribe { view.postBtnEnabled.call(it) }

        view.postBtnClicks
            .bindToLifecycle()
            .withLatestFrom(postTextChanges, { click, text -> text })
            .flatMap {
                micropostNewService.createPost(it.toString())
                    .withProgressDialog()
            }
            .subscribe { micropostNewNavigator.finishWithPost() }
    }

}