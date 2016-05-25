package com.hana053.micropost.presentation.usershow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hana053.micropost.R;
import com.hana053.micropost.databinding.UserShowPostListBinding;
import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.presentation.core.base.BaseFragment;
import com.hana053.micropost.presentation.core.components.micropostlist.PostListAdapter;
import com.hana053.micropost.presentation.core.listeners.OnVerticalScrollListener;
import com.hana053.micropost.presentation.core.services.HttpErrorHandler;
import com.hana053.micropost.presentation.core.services.ProgressBarHandler;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class UserShowPostListFragment extends BaseFragment<List<Micropost>, UserShowPostListBinding> {

    private static final String KEY_USER_ID = "KEY_USER_ID";

    @Inject
    UserShowService userShowService;

    @Inject
    PostListAdapter postListAdapter;

    @Inject
    HttpErrorHandler httpErrorHandler;

    @Inject
    ProgressBarHandler progressBarHandler;

    private final RecyclerView.OnScrollListener prevLoader = new OnVerticalScrollListener() {
        @Override
        public void onScrolledToBottom() {
            loadPosts();
        }
    };

    public static UserShowPostListFragment newInstance(long userId) {
        final UserShowPostListFragment fragment = new UserShowPostListFragment();
        final Bundle args = new Bundle();
        args.putLong(KEY_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_show_post_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RecyclerView postRecyclerView = getBinding().postRecyclerView;
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postRecyclerView.setAdapter(postListAdapter);
        postRecyclerView.addOnScrollListener(prevLoader);

        postListAdapter.addAll(0, getViewModel());
        loadPosts();
    }

    @Override
    protected void inject() {
        getComponent(UserShowComponent.class).inject(this);
    }

    @Override
    protected List<Micropost> initViewModel() {
        return new ArrayList<>();
    }

    @Override
    protected UserShowPostListBinding setupBinding(List<Micropost> viewModel) {
        return UserShowPostListBinding.bind(getView());
    }

    @Override
    protected void saveViewModelState(List<Micropost> viewModel) {
        viewModel.clear();
        viewModel.addAll(postListAdapter.getItems());
    }

    private void loadPosts() {
        final Subscription subscription = userShowService.loadPosts(getUserId())
                .doOnSubscribe(progressBarHandler::show)
                .doAfterTerminate(progressBarHandler::hide)
                .subscribe(posts -> {
                }, httpErrorHandler::handleError);
        collectSubscription(subscription);
    }

    private long getUserId() {
        return getArguments().getLong(KEY_USER_ID);
    }

}
