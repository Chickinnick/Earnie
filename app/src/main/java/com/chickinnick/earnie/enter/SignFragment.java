package com.chickinnick.earnie.enter;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.FragmentSignBinding;


public class SignFragment extends Fragment implements View.OnClickListener {


    private FragmentActionListener onFragmentActionListener;
    private FragmentSignBinding fragmentSignBinding;

    public SignFragment() {
    }

    public static SignFragment newInstance() {
        SignFragment fragment = new SignFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.fade));
            setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.fade));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSignBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign, container, false);
        fragmentSignBinding.signUpBtn.setOnClickListener(this);
        return fragmentSignBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                //TODO: Validation of strings
                String email = fragmentSignBinding.email.getText().toString();
                String password = fragmentSignBinding.password.getText().toString();
                onFragmentActionListener.onLogIn(email, password);
                break;
            case R.id.sign_up_btn:
                onFragmentActionListener.onGoSignUp();
                break;
        }
    }

    public void setOnFragmentActionListener(FragmentActionListener onFragmentActionListener) {
        this.onFragmentActionListener = onFragmentActionListener;
    }
}
