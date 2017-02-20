package com.hana053.micropost.pages

import android.content.Context
import android.view.ViewGroup


interface ViewWrapper {
    val content: ViewGroup

    fun context(): Context = content.context
}