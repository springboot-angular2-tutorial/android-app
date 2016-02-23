package com.hana053.micropost.presentation.usershow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hana053.micropost.R;
import com.hana053.micropost.domain.User;
import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;
import com.hana053.micropost.presentation.core.services.LoginService;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;

@SuppressWarnings("ConstantConditions")
public class UserShowActivity extends AppCompatActivity implements HasComponent<UserShowComponent> {

    public static final String KEY_USER = "KEY_USER";

    @Getter
    private UserShowComponent component;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    LoginService loginService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final User user = Parcels.unwrap(getIntent().getExtras().getParcelable(KEY_USER));

        component = DaggerUserShowComponent.builder()
                .appComponent(BaseApplication.component(this))
                .activityModule(new ActivityModule(this))
                .userShowModule(new UserShowModule())
                .build();
        component.inject(this);
        if (!loginService.ensureAuthenticated()) return;

        setContentView(R.layout.layout_user_show);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, UserShowFragment.newInstance(user))
                    .replace(R.id.postListContainer, UserShowPostListFragment.newInstance(user.getId()))
                    .commit();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
