package com.hana053.micropost.ui.pages.top;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hana053.micropost.BaseApplication;
import com.hana053.micropost.R;
import com.hana053.micropost.services.AuthTokenService;
import com.hana053.micropost.ui.ActivityModule;

import javax.inject.Inject;

public class TopActivity extends AppCompatActivity {

    @Inject
    AuthTokenService authTokenService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TopComponent component = BaseApplication.component(this)
                .topComponent(new ActivityModule(this));
        component.inject(this);
        setContentView(R.layout.layout_top);
    }

}
