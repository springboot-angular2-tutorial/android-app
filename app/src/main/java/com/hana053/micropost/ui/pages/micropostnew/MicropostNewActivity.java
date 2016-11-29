package com.hana053.micropost.ui.pages.micropostnew;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hana053.micropost.R;
import com.hana053.micropost.BaseApplication;
import com.hana053.micropost.ui.ActivityModule;
import com.hana053.micropost.di.HasComponent;
import com.hana053.micropost.services.LoginService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

@SuppressWarnings("ConstantConditions")
public class MicropostNewActivity extends AppCompatActivity implements MicropostNewCtrl, HasComponent<MicropostNewComponent> {

    @Getter
    private MicropostNewComponent component;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = BaseApplication.component(this)
                .activityComponent(new ActivityModule(this))
                .micropostNewComponent(new MicropostNewModule(this));
        component.inject(this);
        if (!loginService.ensureAuthenticated()) return;

        setContentView(R.layout.layout_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new MicropostNewFragment())
                    .commit();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_36dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.micropost_new, menu);
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void finishWithPost() {
        setResult(RESULT_OK);
        finish();
    }

}
