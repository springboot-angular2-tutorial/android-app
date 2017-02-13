package com.hana053.micropost.interactors;

import com.hana053.micropost.BuildConfig;
import com.hana053.micropost.services.AuthTokenService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@SuppressWarnings("WeakerAccess")
public class InteractorModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(AuthTokenService authTokenService) {
        return new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    String authToken = authTokenService.getAuthToken();

                    Request.Builder builder = original.newBuilder();
                    if (authToken != null) builder.header("authorization", "Bearer " + authToken);
                    Request request = builder
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public LoginInteractor provideLoginInteractor(Retrofit retrofit) {
        return retrofit.create(LoginInteractor.class);
    }

}
