package com.chickinnick.earnie.enter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.FragmentRegBinding;

public class RegisterFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private SigninActivity onFragmentActionListener;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRegBinding fragmentRegisterBinding =
                DataBindingUtil.inflate(inflater ,R.layout.fragment_reg, container, false);
        fragmentRegisterBinding.signUp.setOnClickListener(this);
        fragmentRegisterBinding.tickTerms.setOnCheckedChangeListener(this);
        return fragmentRegisterBinding.getRoot();
    }

    public void setOnFragmentActionListener(SigninActivity onFragmentActionListener) {
        this.onFragmentActionListener = onFragmentActionListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up:
                onFragmentActionListener.onSignUp();
                break;
            case R.id.text_agree:
                onFragmentActionListener.onGoReadTerms();
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
