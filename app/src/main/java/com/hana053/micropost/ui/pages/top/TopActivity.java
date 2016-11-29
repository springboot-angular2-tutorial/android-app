package com.hana053.micropost.ui.pages.top;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hana053.micropost.R;
import com.hana053.micropost.BaseApplication;
import com.hana053.micropost.di.HasComponent;
import com.hana053.micropost.ui.ActivityModule;

import lombok.Getter;

public class TopActivity extends AppCompatActivity implements HasComponent<TopComponent> {

    @Getter
    private TopComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = BaseApplication.component(this)
                .activityComponent(new ActivityModule(this))
                .topComponent();
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
