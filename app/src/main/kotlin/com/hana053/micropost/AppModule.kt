package com.hana053.micropost

import com.github.salomonbrys.kodein.Kodein
import com.hana053.micropost.service.serviceModule
import com.hana053.micropost.interactor.interactorModule
import com.hana053.micropost.repository.repositoryModule
import com.hana053.micropost.system.systemModule

fun appModule() = Kodein.Module {

    import(systemModule())
    import(repositoryModule())
    import(interactorModule())
    import(serviceModule())

}

