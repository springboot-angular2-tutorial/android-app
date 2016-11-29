package com.hana053.micropost.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.hana053.micropost.interactors.LoginInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("WeakerAccess")
@Module
public class ServiceModule {

    @Provides
    @Singleton
    public AuthTokenService provideAuthTokenService(SharedPreferences sharedPreferences) {
        return new AuthTokenServiceImpl(sharedPreferences);
    }

    @Provides
    @Singleton
    public LoginService provideLoginService(LoginInteractor loginInteractor,
                                            AuthTokenService authTokenService,
                                            Context context) {
        return new LoginServiceImpl(loginInteractor, authTokenService, context);
    }

    @Provides
    @Singleton
    public HttpErrorHandler provideHttpErrorHandler(Context context,
                                                    LoginService loginService) {
        return new HttpErrorHandlerImpl(context, loginService);
    }
}
