package com.hana053.micropost.system

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton


fun systemServiceModule() = Kodein.Module {

    bind<SharedPreferences>() with singleton {
        PreferenceManager.getDefaultSharedPreferences(instance())
    }

}
