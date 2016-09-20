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
import com.chickinnick.earnie.databinding.FragmentCashBinding;

public class CashFragment extends Fragment implements View.OnClickListener {


    public CashFragment() {
    }

    public static CashFragment newInstance() {
        CashFragment fragment = new CashFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCashBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cash, container, false);
        Typeface typeface = EarineApp.getRegularTypeface();
        binding.cashTv.setTypeface(typeface);
        binding.amountValueEt.setTypeface(typeface);
        binding.confirmBtn.setTypeface(typeface);
        binding.cashAllTv.setTypeface(typeface);
        binding.confirmBtn.setOnClickListener(this);


        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {

    }
}
