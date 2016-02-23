package com.hana053.micropost.presentation.signup;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextWatcher;
import android.view.View;

import com.hana053.micropost.presentation.core.base.BaseFragment;
import com.hana053.micropost.presentation.core.listeners.SimpleTextWatcher;

import org.parceler.Parcels;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public abstract class SignupBaseFragment<B extends ViewDataBinding> extends BaseFragment<SignupViewModel, B> {

    @Inject
    SignupCtrl ctrl;

    @Getter(AccessLevel.PROTECTED)
    private final NextBtnHandler btnHandler = new NextBtnHandler();

    private final BehaviorSubject<Boolean> formSubject = BehaviorSubject.create(false);

    @Getter(AccessLevel.PROTECTED)
    private final Observable<Boolean> formObservable = formSubject
            .debounce(300, TimeUnit.MILLISECONDS, Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(this::showOrHideErrorMsg)
            .doOnNext(btnHandler::setEnabled);

    @Getter
    public TextWatcher formWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            btnHandler.setEnabled(isFormValid());
            formSubject.onNext(isFormValid());
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Subscription subscription = getFormObservable().subscribe();
        collectSubscription(subscription);
    }

    protected Func1<Func1<SignupCtrl, Boolean>, View.OnClickListener> nextBtnHandler = f -> v -> {
        saveViewModelState(getViewModel());
        f.call(ctrl);
    };

    @Override
    protected SignupViewModel initViewModel() {
        return Parcels.unwrap(getArguments().getParcelable(KEY_VIEW_MODEL));
    }

    protected abstract boolean isFormValid();

    protected abstract void showOrHideErrorMsg(boolean aBoolean);

    protected void setViewModelAsArguments(SignupViewModel viewModel) {
        final Bundle args = new Bundle();
        args.putParcelable(KEY_VIEW_MODEL, Parcels.wrap(viewModel));
        setArguments(args);
    }

}
