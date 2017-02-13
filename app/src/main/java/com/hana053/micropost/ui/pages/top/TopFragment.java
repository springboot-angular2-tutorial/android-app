package com.hana053.micropost.ui.pages.top;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hana053.micropost.ui.BaseFragment;
import com.hana053.micropost.R;
import com.hana053.micropost.databinding.TopBinding;
import com.hana053.micropost.ui.Navigator;

import javax.inject.Inject;

public class TopFragment extends BaseFragment<Void, TopBinding> implements TopViewListener {

    @Inject
    Navigator navigator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.top, container, false);
    }

    @Override
    protected void inject() {
        getComponent(TopComponent.class).inject(this);
    }

    @Override
    protected Void initViewModel() {
        return null;
    }

    @Override
    protected TopBinding setupBinding(Void viewModel) {
        TopBinding binding = TopBinding.bind(getView());
        binding.setListener(this);
        return binding;
    }

    @Override
    protected void saveViewModelState(Void viewModel) {
    }

}
