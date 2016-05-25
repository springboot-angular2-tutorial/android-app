package com.hana053.micropost.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hana053.micropost.R;
import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;
import com.hana053.micropost.presentation.core.services.Navigator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

@SuppressWarnings("ConstantConditions")
public class LoginActivity extends AppCompatActivity implements HasComponent<LoginComponent> {

    @Getter
    private LoginComponent component;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerLoginComponent.builder()
                .appComponent(BaseApplication.component(this))
                .activityModule(new ActivityModule(this))
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signup:
                navigator.navigateToSignup();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
