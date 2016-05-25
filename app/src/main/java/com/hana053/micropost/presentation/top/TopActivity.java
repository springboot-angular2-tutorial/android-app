package com.hana053.micropost.presentation.top;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hana053.micropost.R;
import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;

import lombok.Getter;

public class TopActivity extends AppCompatActivity implements HasComponent<TopComponent> {

    @Getter
    private TopComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerTopComponent.builder()
                .appComponent(BaseApplication.component(this))
                .activityModule(new ActivityModule(this))
                .build();
        component.inject(this);
        setContentView(R.layout.layout_top);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new TopFragment())
                    .commit();
        }
    }

}
