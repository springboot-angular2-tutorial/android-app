package com.hana053.micropost.presentation.relateduserlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hana053.micropost.R;
import com.hana053.micropost.databinding.RelatedUserListBinding;
import com.hana053.micropost.domain.RelatedUser;
import com.hana053.micropost.presentation.core.base.BaseFragment;
import com.hana053.micropost.presentation.core.components.followbtn.FollowBtnService;
import com.hana053.micropost.presentation.core.di.ExplicitHasComponent;
import com.hana053.micropost.presentation.core.listeners.OnVerticalScrollListener;
import com.hana053.micropost.presentation.core.services.HttpErrorHandler;
import com.hana053.micropost.presentation.core.services.ProgressBarHandler;
import com.hana053.micropost.presentation.relateduserlist.followerlist.FollowerListComponent;
import com.hana053.micropost.presentation.relateduserlist.followinglist.FollowingListComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class RelatedUserListFragment extends BaseFragment<List<RelatedUser>, RelatedUserListBinding> {

    private static final String KEY_USER_ID = "KEY_USER_ID";

    @Inject
    RelatedUserListAdapter userListAdapter;

    @Inject
    RelatedUserListService relatedUserListService;

    @Inject
    HttpErrorHandler httpErrorHandler;

    @Inject
    ProgressBarHandler progressBarHandler;

    @Inject
    FollowBtnService followBtnService;

    private final RecyclerView.OnScrollListener prevLoader = new OnVerticalScrollListener() {
        @Override
        public void onScrolledToBottom() {
            loadUsers();
        }
    };

    public static RelatedUserListFragment newInstance(long userId) {
        final RelatedUserListFragment fragment = new RelatedUserListFragment();
        final Bundle args = new Bundle();
        args.putLong(KEY_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.related_user_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RecyclerView recyclerView = getBinding().userRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(userListAdapter);
        recyclerView.addOnScrollListener(prevLoader);

        userListAdapter.addAll(0, getViewModel());
        loadUsers();
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
        final Class componentType = ((ExplicitHasComponent) getActivity()).getComponentType();
        if (componentType == FollowingListComponent.class)
            getComponent(FollowingListComponent.class).inject(this);
        else if (componentType == FollowerListComponent.class)
            getComponent(FollowerListComponent.class).inject(this);
        else
            throw new IllegalArgumentException("Component type must be FollowingListComponent or FollowerListComponent.");
    }

    @Override
    protected List<RelatedUser> initViewModel() {
        return new ArrayList<>();
    }

    @Override
    protected RelatedUserListBinding setupBinding(List<RelatedUser> viewModel) {
        return RelatedUserListBinding.bind(getView());
    }

    @Override
    protected void saveViewModelState(List<RelatedUser> viewModel) {
        viewModel.clear();
        viewModel.addAll(userListAdapter.getItems());
    }

    private void loadUsers() {
        final Subscription subscription = relatedUserListService.loadRelatedUsers(getUserId())
                .doOnSubscribe(progressBarHandler::show)
                .finallyDo(progressBarHandler::hide)
                .subscribe(users -> {
                }, httpErrorHandler::handleError);
        collectSubscription(subscription);
    }

    private long getUserId() {
        return getArguments().getLong(KEY_USER_ID);
    }

}
