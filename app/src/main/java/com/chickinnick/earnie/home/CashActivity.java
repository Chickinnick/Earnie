package com.chickinnick.earnie.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivityCashBinding;

public class CashActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityCashBinding activityCashBinding;

    public static final int EXTRA_FLAG_CASH_OK = 1;
    public static final int EXTRA_FLAG_CASH_NO_EARNIE = 2;

    public static final String EXTRA_FLAG_KEY_CASH = "cash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCashBinding = DataBindingUtil.setContentView(this, R.layout.activity_cash);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        activityCashBinding.drawerBtn.setOnClickListener(this);
        int launchFlag = getIntent().getExtras().getInt(EXTRA_FLAG_KEY_CASH, 0);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CashFragment cashFragment = CashFragment.newInstance();
        NoEarnieFragment noEarnieFragment = NoEarnieFragment.newInstance();

        switch (launchFlag) {
            case EXTRA_FLAG_CASH_OK:
                activityCashBinding.title.setText("Wallet");
                fragmentTransaction.replace(R.id.main_cash_container, cashFragment);
                break;
            case EXTRA_FLAG_CASH_NO_EARNIE:
                activityCashBinding.title.setText("Can't cash it, Why?");
                fragmentTransaction.replace(R.id.main_cash_container, noEarnieFragment);
                break;
        }

        fragmentTransaction.commit();


    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_btn:
                onBackPressed();
                break;
        }
    }
}
