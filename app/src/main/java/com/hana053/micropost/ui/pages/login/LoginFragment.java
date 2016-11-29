package com.hana053.micropost.ui.pages.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hana053.micropost.R;
import com.hana053.micropost.databinding.LoginBinding;
import com.hana053.micropost.BaseFragment;
import com.hana053.micropost.ui.listeners.SimpleTextWatcher;
import com.hana053.micropost.services.HttpErrorHandler;
import com.hana053.micropost.services.LoginService;
import com.hana053.micropost.ui.Navigator;
import com.hana053.micropost.ui.ProgressBarHandler;

import javax.inject.Inject;

import lombok.Getter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;

public class LoginFragment extends BaseFragment<LoginViewModel, LoginBinding> implements LoginViewListener {

    @Inject
    LoginService loginService;

    @Inject
    ProgressBarHandler progressBarHandler;

    @Inject
    HttpErrorHandler httpErrorHandler;

    @Inject
    Navigator navigator;

    @Override
    public View.OnClickListener onClickLoginBtn() {
        return v -> {
            final Subscription subscription = loginService.login(getEmail(), getPassword())
                    .doOnSubscribe(progressBarHandler::show)
                    .doAfterTerminate(progressBarHandler::hide)
                    .subscribe(response -> {
                        navigator.navigateToMain();
                    }, e -> {
                        if (e instanceof HttpException && ((HttpException) e).code() == 401) {
                            Toast.makeText(getContext(), "Email or Password is wrong.", Toast.LENGTH_LONG).show();
                        } else {
                            httpErrorHandler.handleError(e);
                        }
                    });
            collectSubscription(subscription);
        };
    }

    @Getter
    private final TextWatcher formWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            getBinding().loginBtn.setEnabled(isFormValid());
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    protected void inject() {
        getComponent(LoginComponent.class).inject(this);
    }

    @Override
    protected LoginViewModel initViewModel() {
        return new LoginViewModel();
    }

    @Override
    protected LoginBinding setupBinding(LoginViewModel viewModel) {
        LoginBinding binding = LoginBinding.bind(getView());
        binding.setViewModel(viewModel);
        binding.setListener(this);
        return binding;
    }

    @Override
    protected void saveViewModelState(LoginViewModel viewModel) {
        viewModel.email.set(getEmail());
        viewModel.password.set(getPassword());
    }

    private boolean isFormValid() {
        return getEmail().length() > 0 && getPassword().length() > 0;
    }

    private String getEmail() {
        return getBinding().emailEditText.getText().toString();
    }

    private String getPassword() {
        return getBinding().passwordEditText.getText().toString();
    }

}
