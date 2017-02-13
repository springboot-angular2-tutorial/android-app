package com.hana053.micropost.services;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("WeakerAccess")
@Module
public class ServiceModule {

    @Provides
    @Singleton
    public HttpErrorHandler provideHttpErrorHandler(Context context) {
        return new HttpErrorHandlerImpl(context);
    }
}
