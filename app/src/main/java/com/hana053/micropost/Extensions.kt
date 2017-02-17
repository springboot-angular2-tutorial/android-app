package com.hana053.micropost

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.github.salomonbrys.kodein.Kodein
import rx.Observable
import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun Activity.content(): ViewGroup {
    return findViewById(android.R.id.content) as ViewGroup
}

fun Activity.getOverridingModule(clazz: Class<*>): Kodein.Module {
    return (application as BaseApplication).getOverridingModule(clazz)
}

fun <T> Observable<T>.withProgressDialog(container: ViewGroup): Observable<T> {
    return Observable.using({
        val progressBar = ProgressBar(container.context, null, android.R.attr.progressBarStyle)
        progressBar.isIndeterminate = true
        progressBar.visibility = View.VISIBLE

        val rl = RelativeLayout(container.context)
        rl.gravity = Gravity.CENTER
        rl.addView(progressBar)

        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        container.addView(rl, layoutParams)

        progressBar
    }, { this }, {
        it.visibility = View.GONE
    })
}

