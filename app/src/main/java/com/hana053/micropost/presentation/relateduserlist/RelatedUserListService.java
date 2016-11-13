package com.hana053.micropost.presentation.relateduserlist;

import com.hana053.micropost.domain.RelatedUser;
import com.hana053.micropost.interactors.RelatedUserListInteractor;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RelatedUserListService {

    private final RelatedUserListInteractor interactor;
    private final RelatedUserListAdapter userListAdapter;

    public RelatedUserListService(RelatedUserListInteractor interactor, RelatedUserListAdapter userListAdapter) {
        this.interactor = interactor;
        this.userListAdapter = userListAdapter;
    }

    Observable<List<RelatedUser>> loadRelatedUsers(long userId) {
        final Long maxId = userListAdapter.getLastItemId();
        final int itemCount = userListAdapter.getItemCount();

        return interactor.listRelatedUsers(userId, maxId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(users -> userListAdapter.addAll(itemCount, users))
                ;
    }

}
