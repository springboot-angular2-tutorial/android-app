package com.hana053.micropost.presentation.core.di;

import android.content.Context;

import com.hana053.micropost.interactors.FeedInteractor;
import com.hana053.micropost.interactors.InteractorModule;
import com.hana053.micropost.interactors.LoginInteractor;
import com.hana053.micropost.interactors.MicropostInteractor;
import com.hana053.micropost.interactors.RelatedUserListInteractor;
import com.hana053.micropost.interactors.RelationshipInteractor;
import com.hana053.micropost.interactors.UserInteractor;
import com.hana053.micropost.interactors.UserMicropostInteractor;
import com.hana053.micropost.presentation.core.services.AuthTokenService;
import com.hana053.micropost.presentation.core.services.HttpErrorHandler;
import com.hana053.micropost.presentation.core.services.LoginService;
import com.hana053.micropost.system.SystemServicesModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        SystemServicesModule.class,
        InteractorModule.class
})
public interface AppComponent {

    Context context();

    FeedInteractor feedInteractor();

    MicropostInteractor micropostInteractor();

    LoginInteractor loginInteractor();

    UserMicropostInteractor userMicropostInteractor();

    AuthTokenService authTokenService();

    UserInteractor userInteractor();

    RelationshipInteractor relationshipInteractor();

    @Named("followings")
    RelatedUserListInteractor followingsInteractor();

    @Named("followers")
    RelatedUserListInteractor followersInteractor();

    LoginService loginService();

    HttpErrorHandler httpErrorHandler();

}
