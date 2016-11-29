package com.hana053.micropost.ui.pages.micropostnew;

import com.hana053.micropost.domain.Micropost;

import rx.Observable;

interface MicropostNewService {

    Observable<Micropost> createPost(String content);

}
