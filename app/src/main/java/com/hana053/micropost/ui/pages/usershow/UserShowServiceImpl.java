package com.hana053.micropost.ui.pages.usershow;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.interactors.UserMicropostInteractor;
import com.hana053.micropost.ui.components.micropostlist.PostListAdapter;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class UserShowServiceImpl implements UserShowService {

    private final UserMicropostInteractor userMicropostInteractor;
    private final PostListAdapter postAdapter;

    UserShowServiceImpl(UserMicropostInteractor userMicropostInteractor, PostListAdapter postAdapter) {
        this.userMicropostInteractor = userMicropostInteractor;
        this.postAdapter = postAdapter;
    }

    @Override
    public Observable<List<Micropost>> loadPosts(long userId) {
        final Long maxId = postAdapter.getLastItemId();
        final int itemCount = postAdapter.getItemCount();

        return userMicropostInteractor.loadPrevPosts(userId, maxId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(posts -> postAdapter.addAll(itemCount, posts))
                ;
    }

}
