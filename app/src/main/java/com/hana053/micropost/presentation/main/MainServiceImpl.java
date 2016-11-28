package com.hana053.micropost.presentation.main;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.interactors.FeedInteractor;
import com.hana053.micropost.presentation.core.components.micropostlist.PostListAdapter;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class MainServiceImpl implements MainService {

    private final FeedInteractor feedInteractor;
    private final PostListAdapter postListAdapter;

    MainServiceImpl(FeedInteractor feedInteractor, PostListAdapter postListAdapter) {
        this.feedInteractor = feedInteractor;
        this.postListAdapter = postListAdapter;
    }

    @Override
    public Observable<List<Micropost>> loadNextFeed() {
        final Long sinceId = postListAdapter.getFirstItemId();
        return feedInteractor.loadNextFeed(sinceId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(feed -> postListAdapter.addAll(0, feed));
    }

    @Override
    public Observable<List<Micropost>> loadPrevFeed() {
        final Long maxId = postListAdapter.getLastItemId();
        final int itemCount = postListAdapter.getItemCount();
        return feedInteractor.loadPrevFeed(maxId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(feed -> postListAdapter.addAll(itemCount, feed));
    }

}
