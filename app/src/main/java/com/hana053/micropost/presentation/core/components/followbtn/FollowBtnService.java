package com.hana053.micropost.presentation.core.components.followbtn;

import com.hana053.micropost.interactors.RelationshipInteractor;
import com.hana053.micropost.presentation.core.di.ActivityScope;
import com.hana053.micropost.presentation.core.services.HttpErrorHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

@ActivityScope
public class FollowBtnService {

    private final EventBus eventBus = EventBus.getDefault();

    private final RelationshipInteractor relationshipInteractor;
    private final HttpErrorHandler httpErrorHandler;
    private final CompositeSubscription subscriptions;

    @Inject
    public FollowBtnService(RelationshipInteractor relationshipInteractor, HttpErrorHandler httpErrorHandler, CompositeSubscription subscriptions) {
        this.relationshipInteractor = relationshipInteractor;
        this.httpErrorHandler = httpErrorHandler;
        this.subscriptions = subscriptions;
    }

    @Subscribe
    public void onEvent(FollowBtnClickEvent event) {
        final FollowBtnViewModel viewModel = event.getFollowBtnViewModel();

        Observable<Void> observable;

        if (viewModel.isFollowedByMe.get()) {
            observable = unfollow(viewModel.getUserId())
                    .doOnCompleted(() -> viewModel.isFollowedByMe.set(false));
        } else {
            observable = follow(viewModel.getUserId())
                    .doOnCompleted(() -> viewModel.isFollowedByMe.set(true));
        }
        final Subscription subscription = observable
                .doOnSubscribe(() -> viewModel.isEnabled.set(false))
                .finallyDo(() -> viewModel.isEnabled.set(true))
                .subscribe(x -> {
                }, httpErrorHandler::handleError);

        subscriptions.add(subscription);
    }

    public void startObserving() {
        eventBus.register(this);
    }

    public void stopObserving() {
        eventBus.unregister(this);
    }

    private Observable<Void> follow(long userId) {
        return relationshipInteractor.follow(userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    private Observable<Void> unfollow(long userId) {
        return relationshipInteractor.unfollow(userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

}
