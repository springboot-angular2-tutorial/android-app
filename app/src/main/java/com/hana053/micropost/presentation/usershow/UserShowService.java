package com.hana053.micropost.presentation.usershow;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.presentation.core.di.ActivityScope;

import java.util.List;

import rx.Observable;

@ActivityScope
interface UserShowService {

    Observable<List<Micropost>> loadPosts(long userId);

}
