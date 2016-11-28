package com.hana053.micropost.presentation.micropostnew;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.interactors.MicropostInteractor;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class MicropostNewServiceImpl implements MicropostNewService {

    private final MicropostInteractor micropostInteractor;

    MicropostNewServiceImpl(MicropostInteractor micropostInteractor) {
        this.micropostInteractor = micropostInteractor;
    }

    @Override
    public Observable<Micropost> createPost(String content) {
        return micropostInteractor
                .create(Micropost.builder().content(content).build())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
