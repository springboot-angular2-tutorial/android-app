package com.hana053.micropost.presentation.micropostnew;

import com.hana053.micropost.domain.Micropost;

import rx.Observable;

interface MicropostNewService {

    Observable<Micropost> createPost(String content);

}
