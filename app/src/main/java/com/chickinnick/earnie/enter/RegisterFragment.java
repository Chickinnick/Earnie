package com.chickinnick.earnie.enter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.FragmentRegBinding;

public class RegisterFragment extends Fragment {


    public RegisterFragment() {
    }

    public static RegisterFragment newInstance(String param1, String param2) {
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
        return fragmentRegisterBinding.getRoot();
    }

}
