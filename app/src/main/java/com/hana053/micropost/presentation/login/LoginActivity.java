package com.hana053.micropost.presentation.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.hana053.micropost.R;
import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;

@SuppressWarnings("ConstantConditions")
public class LoginActivity extends AppCompatActivity implements HasComponent<LoginComponent> {

    @Getter
    private LoginComponent component;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerLoginComponent.builder()
                .appComponent(BaseApplication.component(this))
                .activityModule(new ActivityModule(this))
                .loginModule(new LoginModule())
                .build();
        component.inject(this);

        setContentView(R.layout.layout_flat);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new LoginFragment())
                    .commit();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

}
