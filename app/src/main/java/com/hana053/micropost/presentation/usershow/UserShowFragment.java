package com.hana053.micropost.presentation.usershow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hana053.micropost.R;
import com.hana053.micropost.databinding.UserShowBinding;
import com.hana053.micropost.domain.User;
import com.hana053.micropost.presentation.core.base.BaseFragment;
import com.hana053.micropost.presentation.core.components.avatar.AvatarViewModel;
import com.hana053.micropost.presentation.core.components.followbtn.FollowBtnService;
import com.hana053.micropost.presentation.core.components.followbtn.FollowBtnViewListener;
import com.hana053.micropost.presentation.core.components.followbtn.FollowBtnViewModel;
import com.hana053.micropost.presentation.core.services.Navigator;

import org.parceler.Parcels;

import javax.inject.Inject;

public class UserShowFragment extends BaseFragment<UserShowViewModel, UserShowBinding> implements UserShowViewListener {

    private static final String KEY_USER = "KEY_USER";

    @Inject
    Navigator navigator;

    @Inject
    FollowBtnService followBtnService;

    public static UserShowFragment newInstance(User user) {
        final UserShowFragment fragment = new UserShowFragment();
        final Bundle args = new Bundle();
        args.putParcelable(KEY_USER, Parcels.wrap(user));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View.OnClickListener onClickFollowings() {
        return v -> navigator.navigateToFollowings(getViewModel().user.getId());
    }

    @Override
    public View.OnClickListener onClickFollowers() {
        return v -> navigator.navigateToFollowers(getViewModel().user.getId());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_show, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        followBtnService.startObserving();
    }

    @Override
    public void onStop() {
        followBtnService.stopObserving();
        super.onStop();
    }

    @Override
    protected void inject() {
        getComponent(UserShowComponent.class).inject(this);
    }

    @Override
    protected UserShowViewModel initViewModel() {
        final User user = Parcels.unwrap(getArguments().getParcelable(KEY_USER));
        return new UserShowViewModel(user);
    }

    @Override
    protected UserShowBinding setupBinding(UserShowViewModel viewModel) {
        final UserShowBinding binding = UserShowBinding.bind(getView());
        binding.setModel(viewModel);
        binding.setListener(this);

        final FollowBtnViewModel followBtnViewModel = new FollowBtnViewModel(viewModel.user.getId(), viewModel.user.getUserStats().isFollowedByMe());
        binding.setFollowBtnModel(followBtnViewModel);
        binding.setFollowBtnListener(new FollowBtnViewListener(followBtnViewModel));

        binding.setAvatarModel(new AvatarViewModel(viewModel.user));

        return binding;
    }

    @Override
    protected void saveViewModelState(UserShowViewModel viewModel) {
    }

}
