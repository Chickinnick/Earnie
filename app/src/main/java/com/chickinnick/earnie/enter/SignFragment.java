package com.chickinnick.earnie.enter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chickinnick.earnie.EarineApp;
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
        Typeface typeface = EarineApp.getRegularTypeface();
        fragmentSignBinding.email.setTypeface(typeface);
        fragmentSignBinding.password.setTypeface(typeface);
        fragmentSignBinding.loginBtn.setTypeface(typeface);
        fragmentSignBinding.orTv.setTypeface(typeface);
        fragmentSignBinding.signUpBtn.setTypeface(typeface);
        fragmentSignBinding.textForgot.setTypeface(typeface);

        String forgotPassString = getString(R.string.forgot_passwrd);
        SpannableString spannableString = new SpannableString(forgotPassString);
        ClickableSpan click = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onFragmentActionListener.onForgotPassword();
            }
        };
        spannableString.setSpan(click,
                0, forgotPassString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        fragmentSignBinding.textForgot.setText(spannableString);
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE),
                0, forgotPassString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        fragmentSignBinding.textForgot.setText(spannableString);



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
