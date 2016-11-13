package com.hana053.micropost.presentation.core.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hana053.micropost.presentation.core.di.HasComponent;

import org.parceler.Parcels;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment<VM, B extends ViewDataBinding> extends Fragment {

    protected static final String KEY_VIEW_MODEL = "KEY_VIEW_MODEL";

    @Getter(AccessLevel.PROTECTED)
    private VM viewModel;

    @Getter
    private B binding;

    @Inject
    CompositeSubscription subscriptions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inject();
        if (savedInstanceState == null)
            viewModel = initViewModel();
        else
            viewModel = Parcels.unwrap(savedInstanceState.getParcelable(KEY_VIEW_MODEL));
        binding = setupBinding(viewModel);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveViewModelState(viewModel);
        outState.putParcelable(KEY_VIEW_MODEL, Parcels.wrap(viewModel));
    }

    @Override
    public void onDestroyView() {
        binding.unbind();
        subscriptions.clear();
        super.onDestroyView();
    }

    protected void collectSubscription(Subscription s) {
        subscriptions.add(s);
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected abstract void inject();

    protected abstract VM initViewModel();

    protected abstract B setupBinding(VM viewModel);

    protected abstract void saveViewModelState(VM viewModel);

}
