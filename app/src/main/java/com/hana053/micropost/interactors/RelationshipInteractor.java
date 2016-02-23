package com.hana053.micropost.interactors;

import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface RelationshipInteractor {

    @POST("relationships/to/{followerId}")
    Observable<Void> follow(@Path("followerId") long followerId);

    @DELETE("relationships/to/{followerId}")
    Observable<Void> unfollow(@Path("followerId") long followerId);
}
