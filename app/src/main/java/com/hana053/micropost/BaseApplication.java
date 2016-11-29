package com.hana053.micropost;

import android.app.Application;
import android.content.Context;

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
