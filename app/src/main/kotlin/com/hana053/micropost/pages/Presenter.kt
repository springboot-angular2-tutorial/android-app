package com.hana053.micropost.pages

import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable


interface Presenter<out T : ViewWrapper> {

    val view: T

    fun bind()

    fun <T> Observable<T>.withProgressDialog(): Observable<T> = Observable.using({
        val progressBar = ProgressBar(view.content.context, null, android.R.attr.progressBarStyle).apply {
            isIndeterminate = true
            visibility = View.VISIBLE
        }
        val rl = RelativeLayout(view.content.context).apply {
            gravity = Gravity.CENTER
            addView(progressBar)
        }
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        view.content.addView(rl, layoutParams)
        progressBar
    }, { this }, {
        it.visibility = View.GONE
    })

    fun <T> Observable<T>.bindToLifecycle(): Observable<T> = bindToLifecycle(view.content)
}