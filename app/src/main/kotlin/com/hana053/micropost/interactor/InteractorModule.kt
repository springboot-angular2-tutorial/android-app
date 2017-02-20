package com.hana053.micropost.interactor

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.hana053.micropost.BuildConfig
import com.hana053.micropost.repository.AuthTokenRepository
import com.hana053.myapp.interactors.FeedInteractor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

fun interactorModule() = Kodein.Module {

    bind<OkHttpClient>() with singleton {
        val authTokenRepository = instance<AuthTokenRepository>()

        OkHttpClient().newBuilder().addInterceptor { chain ->
            val original = chain.request()
            val modified = original.newBuilder().apply {
                authTokenRepository.get()?.let {
                    header("authorization", "Bearer $it")
                }
            }.method(original.method(), original.body()).build()

            chain.proceed(modified)
        }.build()
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
        instance<Retrofit>().create(LoginInteractor::class.java)
    }

    bind<FeedInteractor>() with singleton {
        instance<Retrofit>().create(FeedInteractor::class.java)
    }

    bind<UserInteractor>() with singleton {
        instance<Retrofit>().create(UserInteractor::class.java)
    }

    bind<RelationshipInteractor>() with singleton {
        instance<Retrofit>().create(RelationshipInteractor::class.java)
    }

    bind<UserMicropostInteractor>() with singleton {
        instance<Retrofit>().create(UserMicropostInteractor::class.java)
    }

    bind<MicropostInteractor>() with singleton {
        instance<Retrofit>().create(MicropostInteractor::class.java)
    }

    bind<RelatedUserListInteractor>() with singleton {
        instance<Retrofit>().create(RelatedUserListInteractor::class.java)
    }

}

