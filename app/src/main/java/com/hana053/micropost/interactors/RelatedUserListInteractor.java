package com.hana053.micropost.interactors;

import com.hana053.micropost.domain.RelatedUser;

import java.util.List;

import rx.Observable;

public interface RelatedUserListInteractor {

    Observable<List<RelatedUser>> listRelatedUsers(long userId, Long maxId);
}
