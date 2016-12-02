package com.hana053.micropost.ui.pages.signup.fullname;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hana053.micropost.R;
import com.hana053.micropost.databinding.SignupFullNameBinding;
import com.hana053.micropost.ui.pages.signup.SignupBaseFragment;
import com.hana053.micropost.ui.pages.signup.SignupViewModel;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignupFullNameFragment extends SignupBaseFragment<SignupFullNameBinding> implements SignupFullNameViewListener {

    public static Fragment newInstance(SignupViewModel viewModel) {
        final SignupFullNameFragment fragment = new SignupFullNameFragment();
        fragment.setViewModelAsArguments(viewModel);
        return fragment;
    }

    @Override
    public View.OnClickListener onClickNextBtn() {
        return nextBtnHandler.call(signupCtrl -> {
            signupCtrl.navigateToNewEmail();
            return true;
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_full_name, container, false);
    }

    @Override
    protected SignupFullNameBinding setupBinding(SignupViewModel viewModel) {
        SignupFullNameBinding binding = SignupFullNameBinding.bind(getView());
        binding.setViewModel(viewModel);
        binding.setListener(this);
        binding.setBtnHandler(getBtnHandler());
        return binding;
    }

    @Override
    protected boolean isFormValid() {
        return getFullName().length() >= 4;
    }

    @Override
    protected void showOrHideErrorMsg(boolean isValid) {
        if (isValid || getFullName().length() == 0)
            getBinding().fullNameInvalid.setVisibility(View.GONE);
        else
            getBinding().fullNameInvalid.setVisibility(View.VISIBLE);
    }

    @NonNull
    private String getFullName() {
        return getViewModel().fullName.get();
    }

}
