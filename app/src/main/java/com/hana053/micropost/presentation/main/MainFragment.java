package com.hana053.micropost.presentation.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hana053.micropost.R;
import com.hana053.micropost.databinding.MainBinding;
import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.presentation.core.base.BaseFragment;
import com.hana053.micropost.presentation.core.components.micropostlist.PostListAdapter;
import com.hana053.micropost.presentation.core.listeners.BottomedViewSyncScroller;
import com.hana053.micropost.presentation.core.listeners.OnVerticalScrollListener;
import com.hana053.micropost.presentation.core.services.HttpErrorHandler;
import com.hana053.micropost.presentation.core.services.ProgressBarHandler;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class MainFragment extends BaseFragment<List<Micropost>, MainBinding> implements MainViewListener {

    private static final String KEY_POST_RECYCLER_VIEW_STATE = "KEY_POST_RECYCLER_VIEW_STATE";

    @Inject
    MainCtrl ctrl;

    @Inject
    MainService mainService;

    @Inject
    HttpErrorHandler httpErrorHandler;

    @Inject
    PostListAdapter postListAdapter;

    @Inject
    ProgressBarHandler progressBarHandler;

    private final RecyclerView.OnScrollListener prevFeedLoader = new OnVerticalScrollListener() {
        @Override
        public void onScrolledToBottom() {
            final Subscription subscription = mainService.loadPrevFeed()
                    .doOnSubscribe(progressBarHandler::show)
                    .finallyDo(progressBarHandler::hide)
                    .subscribe(microposts -> {
                    }, httpErrorHandler::handleError);
            collectSubscription(subscription);
        }
    };

    @Override
    public View.OnClickListener onClickNewMicropostBtn() {
        return v -> ctrl.navigateToMicropostNew();
    }

    @Override
    public SwipeRefreshLayout.OnRefreshListener onSwipeRefresh() {
        return () -> {
            final Subscription subscription = mainService.loadNextFeed()
                    .finallyDo(() -> getBinding().swipeRefreshLayout.setRefreshing(false))
                    .subscribe(microposts -> {
                    }, httpErrorHandler::handleError);
            collectSubscription(subscription);
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RecyclerView postRecyclerView = getBinding().postRecyclerView;
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postRecyclerView.setAdapter(postListAdapter);
        postRecyclerView.addOnScrollListener(prevFeedLoader);
        postRecyclerView.addOnScrollListener(new BottomedViewSyncScroller(getBinding().newMicropostBtn));

        if (savedInstanceState != null) {
            getBinding().postRecyclerView.getLayoutManager()
                    .onRestoreInstanceState(savedInstanceState.getParcelable(KEY_POST_RECYCLER_VIEW_STATE));
        }
        postListAdapter.addAll(0, getViewModel());
        loadFeed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_POST_RECYCLER_VIEW_STATE,
                getBinding().postRecyclerView.getLayoutManager().onSaveInstanceState());

    }

    public void loadFeed() {
        final Subscription subscription = mainService.loadNextFeed()
                .doOnSubscribe(progressBarHandler::show)
                .finallyDo(progressBarHandler::hide)
                .subscribe(microposts -> {
                }, httpErrorHandler::handleError);
        collectSubscription(subscription);
    }

    @Override
    protected void inject() {
        getComponent(MainComponent.class).inject(this);
    }

    @Override
    protected List<Micropost> initViewModel() {
        return new ArrayList<>();
    }

    @Override
    protected MainBinding setupBinding(List<Micropost> viewModel) {
        MainBinding binding = MainBinding.bind(getView());
        binding.setListener(this);
        return binding;
    }

    @Override
    protected void saveViewModelState(List<Micropost> viewModel) {
        viewModel.clear();
        viewModel.addAll(postListAdapter.getItems());
    }

}
