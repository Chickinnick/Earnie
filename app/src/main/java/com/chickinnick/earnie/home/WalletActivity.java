package com.chickinnick.earnie.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.R;
import com.chickinnick.earnie.adcore.AdOverlayService;
import com.chickinnick.earnie.databinding.ActivityWalletBinding;
import com.chickinnick.earnie.model.User;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int OVERLAY_PERMISSION_REQUEST_CODE = 1234;
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
        binding.youGotTv.setTypeface(earniesTf);

        User user = new User();
        user.setName("Robin");
        binding.setUser(user);
        binding.userMessage.setTypeface(typeface);


        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
            } else {
                startAdvertService();
            }
        } else {
            startAdvertService();
        }




    }

    private void startAdvertService() {
        Intent intent = new Intent(this, AdOverlayService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_btn:
                startActivity(new Intent(WalletActivity.this, SettingsActivity.class));
                break;
            case R.id.cash_btn:
                Intent intent = new Intent(WalletActivity.this, CashActivity.class);
                if (binding.earniesValue.getText() != "0") {
                    intent.putExtra(CashActivity.EXTRA_FLAG_KEY_CASH, CashActivity.EXTRA_FLAG_CASH_NO_EARNIE);
                } else {
                    intent.putExtra(CashActivity.EXTRA_FLAG_KEY_CASH, CashActivity.EXTRA_FLAG_CASH_OK);
                }
                startActivity(intent);
                break;
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            startAdvertService();
        }
    }
}
