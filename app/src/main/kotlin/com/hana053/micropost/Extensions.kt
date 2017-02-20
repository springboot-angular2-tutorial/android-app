package com.hana053.micropost

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein

fun Activity.getOverridingModule(): Kodein.Module =
    (application as BaseApplication).getOverridingModule(javaClass)
