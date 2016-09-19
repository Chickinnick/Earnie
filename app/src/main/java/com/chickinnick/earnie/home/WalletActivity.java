package com.chickinnick.earnie.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivityWalletBinding;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityWalletBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        Typeface typeface = EarineApp.getRegularTypeface();
        binding.title.setTypeface(typeface);
        binding.drawerBtn.setOnClickListener(this);
        binding.cashBtn.setTypeface(typeface);
        binding.cashBtn.setOnClickListener(this);

        Typeface earniesTf = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
        binding.earniesValue.setText("0");//TODO attach model
        binding.earniesValue.setTypeface(earniesTf);
        binding.earnies.setTypeface(earniesTf);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_btn:
                startActivity(new Intent(WalletActivity.this, SettingsActivity.class));
                break;
            case R.id.cash_btn:
                Intent intent = new Intent(WalletActivity.this, CashActivity.class);
                if (binding.earniesValue.getText() == "0") {
                    intent.putExtra(CashActivity.EXTRA_FLAG_KEY_CASH, CashActivity.EXTRA_FLAG_CASH_NO_EARNIE);
                } else {
                    intent.putExtra(CashActivity.EXTRA_FLAG_KEY_CASH, CashActivity.EXTRA_FLAG_CASH_OK);
                }
                startActivity(intent);
                break;
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}
