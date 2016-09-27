package com.chickinnick.earnie.payment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivityPaymentBinding;
import com.chickinnick.earnie.model.PaymentMethod;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPaymentBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        binding.paymentMethList.setLayoutManager(llm);
        binding.paymentMethList.setHasFixedSize(true);
        binding.paymentMethList.setAdapter(new ListRecAdapter(getPaymentMethods()));
    }

    PaymentMethod[] getPaymentMethods() {
        PaymentMethod[] paymentMethods = {
                new PaymentMethod("Paypal", true),
                new PaymentMethod("Bank account", false),
                new PaymentMethod("Earnie Goods", false),
                new PaymentMethod("Donate", false)
        };
        return paymentMethods;
    }

}
