package com.hana053.micropost.interactors;

import com.hana053.micropost.domain.RelatedUser;
import com.hana053.micropost.domain.User;

import java.util.List;

import lombok.Builder;
import lombok.Value;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface UserInteractor {

    @POST("users")
    Observable<User> create(@Body SignupRequest request);

    @GET("users/{id}/followings")
    Observable<List<RelatedUser>> listFollowings(@Path("id") long userId,
                                                 @Query("maxId") Long maxId);

    @GET("users/{id}/followers")
    Observable<List<RelatedUser>> listFollowers(@Path("id") long userId,
                                                @Query("maxId") Long maxId);

    @Value
    @Builder
    class SignupRequest {
        private final String email;
        private final String password;
        private final String name;
    }

}
