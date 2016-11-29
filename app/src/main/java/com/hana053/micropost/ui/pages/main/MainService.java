package com.hana053.micropost.ui.pages.main;

import com.hana053.micropost.domain.Micropost;

import java.util.List;

import rx.Observable;

interface MainService {

    Observable<List<Micropost>> loadNextFeed();

    Observable<List<Micropost>> loadPrevFeed();

}
