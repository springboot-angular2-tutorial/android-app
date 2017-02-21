package com.hana053.micropost

import android.app.Activity

fun Activity.getOverridingModule() =
    (application as BaseApplication).getOverridingModule(javaClass)
