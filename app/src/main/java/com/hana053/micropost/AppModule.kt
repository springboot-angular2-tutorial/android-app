package com.hana053.micropost

import com.github.salomonbrys.kodein.Kodein
import com.hana053.micropost.activity.activityModule
import com.hana053.micropost.interactors.interactorModule
import com.hana053.micropost.services.serviceModule
import com.hana053.micropost.system.systemServiceModule

fun appModule() = Kodein.Module {

    import(systemServiceModule())
    import(serviceModule())
    import(interactorModule())
    import(activityModule())

}

