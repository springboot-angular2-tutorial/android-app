package com.hana053.micropost.pages.micropostnew

import android.view.ViewGroup
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.widget.textChanges
import kotlinx.android.synthetic.main.activity_micropost_new.view.*


class MicropostNewView(val content: ViewGroup) {

    // Events
    val postTextChanges = content.postEditText.textChanges()
    val postBtnClicks = content.postBtn.clicks()

    // Props
    val postBtnEnabled = content.postBtn.enabled()
}