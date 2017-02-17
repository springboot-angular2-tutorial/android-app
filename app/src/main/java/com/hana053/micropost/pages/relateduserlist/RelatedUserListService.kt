package com.hana053.micropost.pages.relateduserlist

import com.hana053.micropost.domain.RelatedUser
import rx.Observable


interface RelatedUserListService {
    fun listUsers(userId: Long): Observable<List<RelatedUser>>
    fun title(): String
}