package com.hana053.micropost

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein

fun Activity.getOverridingModule(clazz: Class<*>): Kodein.Module {
    return (application as BaseApplication).getOverridingModule(clazz)
}

