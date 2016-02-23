package com.hana053.micropost.presentation.core.base;

import android.app.Application;
import android.content.Context;

import com.hana053.micropost.presentation.core.di.AppComponent;

public abstract class BaseApplication extends Application {

    protected AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = createComponent();
    }

    public static AppComponent component(Context context) {
        return ((BaseApplication) context.getApplicationContext()).component;
    }

    protected abstract AppComponent createComponent();
}
