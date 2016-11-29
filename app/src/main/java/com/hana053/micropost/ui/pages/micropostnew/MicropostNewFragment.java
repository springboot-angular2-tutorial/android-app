package com.hana053.micropost.ui.pages.micropostnew;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hana053.micropost.R;
import com.hana053.micropost.databinding.MicropostNewBinding;
import com.hana053.micropost.ui.BaseFragment;
import com.hana053.micropost.ui.listeners.SimpleTextWatcher;
import com.hana053.micropost.services.HttpErrorHandler;

import javax.inject.Inject;

import lombok.Getter;
import rx.Subscription;

public class MicropostNewFragment extends BaseFragment<MicropostNewViewModel, MicropostNewBinding> implements MicropostNewViewListener {

    @Inject
    MicropostNewService micropostNewService;

    @Inject
    MicropostNewCtrl ctrl;

    @Inject
    HttpErrorHandler httpErrorHandler;

    @Getter
    private final TextWatcher formWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            getBinding().postBtn.setEnabled(isFormValid());
        }
    };

    @Override
    public View.OnClickListener onClickPostBtn() {
        return v -> {
            final Subscription subscription = micropostNewService.createPost(getContent())
                    .doOnCompleted(ctrl::finishWithPost)
                    .subscribe(micropost -> {
                    }, httpErrorHandler::handleError);
            collectSubscription(subscription);
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.micropost_new, container, false);
    }

    @Override
    protected void inject() {
        getComponent(MicropostNewComponent.class).inject(this);
    }

    @Override
    protected MicropostNewViewModel initViewModel() {
        return new MicropostNewViewModel();
    }

    @Override
    protected MicropostNewBinding setupBinding(MicropostNewViewModel viewModel) {
        MicropostNewBinding binding = MicropostNewBinding.bind(getView());
        binding.setViewModel(viewModel);
        binding.setListener(this);
        return binding;
    }

    @Override
    protected void saveViewModelState(MicropostNewViewModel viewModel) {
        viewModel.content.set(getContent());
    }

    @NonNull
    private String getContent() {
        return getBinding().postEditText.getText().toString();
    }

    private boolean isFormValid() {
        return getContent().length() > 0;
    }

}
