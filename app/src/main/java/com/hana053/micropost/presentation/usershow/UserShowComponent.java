package com.hana053.micropost.presentation.usershow;

import com.hana053.micropost.databinding.UserShowBinding;
import com.hana053.micropost.databinding.UserShowPostListBinding;
import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.presentation.core.base.BaseFragment;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.di.AppComponent;

import java.util.List;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                ActivityModule.class,
                UserShowModule.class,
        }
)
public interface UserShowComponent {

    void inject(UserShowActivity activity);

    void inject(UserShowPostListFragment fragment);

    void inject(UserShowFragment fragment);

    // It's required. If it was removed, dagger will cause an error
    @SuppressWarnings("unused")
    void dummy1(BaseFragment<List<Micropost>, UserShowPostListBinding> fragment);

    // It's required. If it was removed, dagger will cause an error
    @SuppressWarnings("unused")
    void dummy2(BaseFragment<UserShowViewModel, UserShowBinding> fragment);
}
