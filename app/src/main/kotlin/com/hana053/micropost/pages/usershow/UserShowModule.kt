package com.hana053.micropost.pages.usershow

import com.github.salomonbrys.kodein.Kodein
import com.hana053.micropost.pages.usershow.detail.userShowDetailModule
import com.hana053.micropost.pages.usershow.posts.userShowPostsModule
import com.hana053.micropost.shared.followbtn.followBtnModule

fun userShowModule() = Kodein.Module {

    import(userShowDetailModule())
    import(userShowPostsModule())
    import(followBtnModule())

}


