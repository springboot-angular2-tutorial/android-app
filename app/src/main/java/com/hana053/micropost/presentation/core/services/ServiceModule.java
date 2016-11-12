package com.hana053.micropost.presentation.core.services;


import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    AuthTokenService provideAuthTokenService(SharedPreferences sharedPreferences) {
        return new AuthTokenServiceImpl(sharedPreferences);
    }

}
