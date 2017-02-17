package com.hana053.micropost.interactors

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.hana053.micropost.BuildConfig
import com.hana053.micropost.services.AuthTokenService
import com.hana053.myapp.interactors.FeedInteractor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

fun interactorModule() = Kodein.Module {

    bind<OkHttpClient>() with singleton {
        val authTokenService = instance<AuthTokenService>()

        OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                val authToken = authTokenService.getAuthToken()
                val original = chain.request()
                val builder = original.newBuilder()
                if (authToken != null)
                    builder.header("authorization", "Bearer " + authToken)
                val request = builder
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    bind<Retrofit>() with singleton {
        val okHttpClient = instance<OkHttpClient>()

        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    bind<LoginInteractor>() with singleton {
        val retrofit = instance<Retrofit>()
        retrofit.create(LoginInteractor::class.java)
    }

    bind<FeedInteractor>() with singleton {
        val retrofit = instance<Retrofit>()
        retrofit.create(FeedInteractor::class.java)
    }

    bind<UserInteractor>() with singleton {
        val retrofit = instance<Retrofit>()
        retrofit.create(UserInteractor::class.java)
    }

    bind<RelationshipInteractor>() with singleton {
        val retrofit = instance<Retrofit>()
        retrofit.create(RelationshipInteractor::class.java)
    }

    bind<UserMicropostInteractor>() with singleton {
        val retrofit = instance<Retrofit>()
        retrofit.create(UserMicropostInteractor::class.java)
    }

    bind<MicropostInteractor>() with singleton {
        val retrofit = instance<Retrofit>()
        retrofit.create(MicropostInteractor::class.java)
    }

    bind<RelatedUserListInteractor>() with singleton {
        val retrofit = instance<Retrofit>()
        retrofit.create(RelatedUserListInteractor::class.java)
    }

}

