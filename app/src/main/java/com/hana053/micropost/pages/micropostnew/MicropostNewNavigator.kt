package com.hana053.micropost.pages.micropostnew

import android.app.Activity


class MicropostNewNavigator(
    private val activity: Activity
) {
    fun finishWithPost() {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }
}