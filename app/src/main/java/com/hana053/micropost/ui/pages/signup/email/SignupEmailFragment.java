package com.hana053.micropost.ui.pages.signup.email;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hana053.micropost.R;
import com.hana053.micropost.databinding.SignupEmailBinding;
import com.hana053.micropost.ui.pages.signup.SignupBaseFragment;
import com.hana053.micropost.ui.pages.signup.SignupViewModel;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignupEmailFragment extends SignupBaseFragment<SignupEmailBinding> implements SignupEmailViewListener {

    public static Fragment newInstance(SignupViewModel viewModel) {
        final SignupEmailFragment fragment = new SignupEmailFragment();
        fragment.setViewModelAsArguments(viewModel);
        return fragment;
    }

    @Override
    public View.OnClickListener onClickNextBtn() {
        return nextBtnHandler.call(signupCtrl -> {
            signupCtrl.navigateToNewPassword();
            return true;
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_email, container, false);
    }

    @Override
    protected SignupEmailBinding setupBinding(SignupViewModel viewModel) {
        SignupEmailBinding binding = SignupEmailBinding.bind(getView());
        binding.setViewModel(viewModel);
        binding.setListener(this);
        binding.setBtnHandler(getBtnHandler());
        return binding;
    }

    @Override
    protected boolean isFormValid() {
        final String emailPattern = "^[^0-9][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@\\[?([\\d\\w\\.-]+)]?$";
        return getEmail().matches(emailPattern);
    }

    @Override
    protected void showOrHideErrorMsg(boolean isValid) {
        if (isValid || getEmail().length() == 0)
            getBinding().emailInvalid.setVisibility(View.GONE);
        else
            getBinding().emailInvalid.setVisibility(View.VISIBLE);
    }

    @NonNull
    private String getEmail() {
        return getViewModel().email.get();
    }

}
