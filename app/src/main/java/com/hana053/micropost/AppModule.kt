package com.hana053.micropost

import com.github.salomonbrys.kodein.Kodein
import com.hana053.micropost.service.serviceModule
import com.hana053.micropost.interactor.interactorModule
import com.hana053.micropost.repository.repositoryModule
import com.hana053.micropost.system.systemServiceModule

fun appModule() = Kodein.Module {

    import(systemServiceModule())
    import(repositoryModule())
    import(interactorModule())
    import(serviceModule())

}

