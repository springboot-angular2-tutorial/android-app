package com.hana053.micropost.ui.pages.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hana053.micropost.BaseApplication;
import com.hana053.micropost.R;
import com.hana053.micropost.di.HasComponent;
import com.hana053.micropost.services.LoginService;
import com.hana053.micropost.ui.ActivityModule;
import com.hana053.micropost.ui.Navigator;
import com.hana053.micropost.ui.components.avatar.AvatarClickEvent;
import com.hana053.micropost.ui.pages.micropostnew.MicropostNewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

public class MainActivity extends AppCompatActivity implements MainCtrl, HasComponent<MainComponent> {

    static final int REQUEST_POST = 1;
    static final String TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT";

    @Getter
    private MainComponent component;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    LoginService loginService;

    @Inject
    Navigator navigator;

    private MainFragment fragment;

    private final EventBus eventBus = EventBus.getDefault();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = BaseApplication.component(this)
                .activityComponent(new ActivityModule(this))
                .mainComponent(new MainModule(this));
        component.inject(this);
        if (!loginService.ensureAuthenticated()) return;

        setContentView(R.layout.layout_main);
        ButterKnife.bind(this);

        FragmentManager fm = getSupportFragmentManager();
        fragment = (MainFragment) fm.findFragmentByTag(TAG_MAIN_FRAGMENT);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    .add(R.id.container, fragment, TAG_MAIN_FRAGMENT)
                    .commit();
        }

        setSupportActionBar(toolbar);
    }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                loginService.logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_POST && resultCode == RESULT_OK) {
            fragment.loadFeed();
        }
    }

    @Override
    public void navigateToMicropostNew() {
        final Intent intent = new Intent(this, MicropostNewActivity.class);
        startActivityForResult(intent, REQUEST_POST);
        overridePendingTransition(R.anim.slide_in_up, 0);
    }

    @Subscribe
    public void onEvent(AvatarClickEvent event) {
        navigator.navigateToUserShow(event.getUser());
    }

}
