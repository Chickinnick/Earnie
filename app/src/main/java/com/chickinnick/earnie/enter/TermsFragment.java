package com.chickinnick.earnie.enter;


import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.FragmentTerBinding;

public class TermsFragment extends Fragment {


    private FragmentActionListener fragmentActionListener;

    public TermsFragment() {
    }

    public static TermsFragment newInstance() {
        TermsFragment fragment = new TermsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTerBinding termsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ter, container, false);
        Typeface typeface = EarineApp.getRegularTypeface();
        termsBinding.backBtn.setTypeface(typeface);
        termsBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        termsBinding.termsTitle.setTypeface(typeface);
        termsBinding.termsText.setTypeface(typeface);

        return termsBinding.getRoot();
    }

    public void setOnFragmentActionListener(FragmentActionListener fragmentActionListener) {
        this.fragmentActionListener = fragmentActionListener;
    }

}
