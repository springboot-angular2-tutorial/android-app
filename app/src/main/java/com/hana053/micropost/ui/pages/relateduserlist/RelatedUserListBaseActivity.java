package com.hana053.micropost.ui.pages.relateduserlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hana053.micropost.R;
import com.hana053.micropost.services.LoginService;
import com.hana053.micropost.ui.Navigator;
import com.hana053.micropost.ui.components.avatar.AvatarClickEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ConstantConditions")
public abstract class RelatedUserListBaseActivity extends AppCompatActivity {

    public static final String KEY_USER_ID = "KEY_USER_ID";

    private final EventBus eventBus = EventBus.getDefault();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    LoginService loginService;

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long userId = getIntent().getExtras().getLong(KEY_USER_ID);

        prepareComponent();
        if (!loginService.ensureAuthenticated()) return;

        setContentView(R.layout.layout_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, RelatedUserListFragment.newInstance(userId))
                    .commit();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected abstract void prepareComponent();

    @Override
    protected void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    @Override
    protected void onStop() {
        eventBus.unregister(this);
        super.onStop();
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

    @Subscribe
    public void onEvent(AvatarClickEvent event) {
        navigator.navigateToUserShow(event.getUser());
    }

}
