package com.hana053.micropost.interactors;

import com.hana053.micropost.BuildConfig;
import com.hana053.micropost.presentation.core.services.AuthTokenService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class InteractorModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(AuthTokenService authTokenService) {
        return new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    String authToken = authTokenService.getAuthToken();

                    Request.Builder builder = original.newBuilder();
                    if (authToken != null) builder.header("X-AUTH-TOKEN", authToken);
                    Request request = builder
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    FeedInteractor provideFeedInteractor(Retrofit retrofit) {
        return retrofit.create(FeedInteractor.class);
    }

    @Provides
    @Singleton
    MicropostInteractor provideMicropostInteractor(Retrofit retrofit) {
        return retrofit.create(MicropostInteractor.class);
    }

    @Provides
    @Singleton
    LoginInteractor provideLoginInteractor(Retrofit retrofit) {
        return retrofit.create(LoginInteractor.class);
    }

    @Provides
    @Singleton
    UserInteractor provideUserInteractor(Retrofit retrofit) {
        return retrofit.create(UserInteractor.class);
    }

    @Provides
    @Singleton
    UserMicropostInteractor provideUserMicropostInteractor(Retrofit retrofit) {
        return retrofit.create(UserMicropostInteractor.class);
    }

    @Provides
    @Singleton
    RelationshipInteractor provideRelationshipInteractor(Retrofit retrofit) {
        return retrofit.create(RelationshipInteractor.class);
    }

    @Provides
    @Singleton
    @Named("followings")
    RelatedUserListInteractor provideFollowingsInteractor(UserInteractor userInteractor) {
        // retrofit does not allow inheritance of interface. So, I take this way.
        return userInteractor::listFollowings;
    }

    @Provides
    @Singleton
    @Named("followers")
    RelatedUserListInteractor provideFollowersInteractor(UserInteractor userInteractor) {
        // retrofit does not allow inheritance of interface. So, I take this way.
        return userInteractor::listFollowers;
    }

}
