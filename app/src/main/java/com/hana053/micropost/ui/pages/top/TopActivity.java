package com.hana053.micropost.ui.pages.top;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hana053.micropost.BaseApplication;
import com.hana053.micropost.R;
import com.hana053.micropost.ui.ActivityModule;

public class TopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TopComponent component = BaseApplication.component(this)
                .activityComponent(new ActivityModule(this))
                .topComponent();
        component.inject(this);
        setContentView(R.layout.layout_top);
    }

}
