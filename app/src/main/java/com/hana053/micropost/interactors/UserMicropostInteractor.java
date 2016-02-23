package com.hana053.micropost.interactors;

import android.support.annotation.Nullable;

import com.hana053.micropost.domain.Micropost;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface UserMicropostInteractor {

    @GET("users/{userId}/microposts?count=20")
    Observable<List<Micropost>> loadPrevPosts(@Path("userId") long userId, @Nullable @Query("maxId") Long maxId);
}
