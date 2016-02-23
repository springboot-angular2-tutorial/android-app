package com.hana053.micropost.presentation.core.services;

import android.content.Context;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.adapter.rxjava.HttpException;
import timber.log.Timber;

@Singleton
public class HttpErrorHandler {

    private final Context context;
    private final LoginService loginService;

    @Inject
    HttpErrorHandler(Context context, LoginService loginService) {
        this.context = context;
        this.loginService = loginService;
    }

    public void handleError(Throwable throwable) {
        try {
            throw throwable;
        } catch (SocketTimeoutException e) {
            Toast.makeText(context, "Connection timed out.", Toast.LENGTH_LONG).show();
        } catch (ConnectException e) {
            Toast.makeText(context, "Cannot connect to server.", Toast.LENGTH_LONG).show();
        } catch (HttpException e) {
            if (e.code() == 401) {
                Toast.makeText(context, "Please sign in.", Toast.LENGTH_LONG).show();
                loginService.logout();
            } else if (e.code() >= 500) {
                Toast.makeText(context, "Something bad happened.", Toast.LENGTH_LONG).show();
            }
        } catch (Throwable e) {
            Timber.e("handleHttpError: %s", e);
            Toast.makeText(context, "Something bad happened.", Toast.LENGTH_LONG).show();
        }
    }
}
