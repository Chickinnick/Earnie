package com.chickinnick.earnie.enter;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.chickinnick.earnie.EarineApp;
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
        fragmentRegisterBinding.textAgree.setMovementMethod(LinkMovementMethod.getInstance());
        fragmentRegisterBinding.textAgree.setText(initStringWithLink());

        Typeface typeface = EarineApp.getRegularTypeface();
        fragmentRegisterBinding.signUp.setTypeface(typeface);
        fragmentRegisterBinding.firstName.setTypeface(typeface);
        fragmentRegisterBinding.lastName.setTypeface(typeface);
        fragmentRegisterBinding.email.setTypeface(typeface);
        fragmentRegisterBinding.password.setTypeface(typeface);
        fragmentRegisterBinding.orTv.setTypeface(typeface);
        fragmentRegisterBinding.textAgree.setTypeface(typeface);



        return fragmentRegisterBinding.getRoot();
    }

    private SpannableString initStringWithLink() {
        String termsString = getString(R.string.terms_of_service);
        String agreeString = getString(R.string.i_agree, termsString);
        SpannableString spannableString = new SpannableString(agreeString);
        int startIndex = agreeString.indexOf(termsString);
        ClickableSpan click = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onFragmentActionListener.onGoReadTerms();
            }
        };
        spannableString.setSpan(click,
                startIndex, startIndex + termsString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;

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

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
