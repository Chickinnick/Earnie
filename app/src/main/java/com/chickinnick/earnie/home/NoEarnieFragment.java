package com.chickinnick.earnie.home;


import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.FragmentNoEarnieBinding;

public class NoEarnieFragment extends Fragment implements View.OnClickListener {


    public NoEarnieFragment() {
    }

    public static NoEarnieFragment newInstance() {
        NoEarnieFragment fragment = new NoEarnieFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNoEarnieBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_no_earnie, container, false);
        Typeface typeface = EarineApp.getRegularTypeface();
        dataBinding.cannotCashTv.setTypeface(typeface);
        dataBinding.paymentMethodBtn.setTypeface(typeface);
        dataBinding.paymentMethodBtn.setOnClickListener(this);

        return dataBinding.getRoot();
    }

    @Override
    public void onClick(View v) {

    }
}
