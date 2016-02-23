package com.hana053.micropost.presentation.usershow;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.interactors.UserMicropostInteractor;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.components.micropostlist.PostListAdapter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ActivityScope
public class UserShowService {

    private final UserMicropostInteractor userMicropostInteractor;
    private final PostListAdapter postAdapter;

    @Inject
    public UserShowService(UserMicropostInteractor userMicropostInteractor, PostListAdapter postAdapter) {
        this.userMicropostInteractor = userMicropostInteractor;
        this.postAdapter = postAdapter;
    }

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
