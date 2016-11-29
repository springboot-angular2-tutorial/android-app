package com.hana053.micropost.ui.pages.usershow;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.ui.ActivityScope;

import java.util.List;

import rx.Observable;

@ActivityScope
interface UserShowService {

    Observable<List<Micropost>> loadPosts(long userId);

}
