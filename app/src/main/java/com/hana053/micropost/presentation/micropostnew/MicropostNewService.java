package com.hana053.micropost.presentation.micropostnew;

import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.interactors.MicropostInteractor;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ActivityScope
class MicropostNewService {

    private final MicropostInteractor micropostInteractor;

    @Inject
    MicropostNewService(MicropostInteractor micropostInteractor) {
        this.micropostInteractor = micropostInteractor;
    }

    Observable<Micropost> createPost(String content) {
        return micropostInteractor
                .create(Micropost.builder().content(content).build())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
