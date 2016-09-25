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
import android.view.WindowManager;

import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.R;
import com.chickinnick.earnie.adcore.AdOverlayService;
import com.chickinnick.earnie.databinding.ActivityWalletBinding;
import com.chickinnick.earnie.model.User;

import io.paperdb.Paper;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int OVERLAY_PERMISSION_REQUEST_CODE = 1234;
    public static final String EXTRA_NEW_EARNIE = "new_earnie";
    private ActivityWalletBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);



        Typeface typeface = EarineApp.getRegularTypeface();
        binding.title.setTypeface(typeface);
        binding.drawerBtn.setOnClickListener(this);
        binding.cashBtn.setTypeface(typeface);
        binding.cashBtn.setOnClickListener(this);

        Typeface earniesTf = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
        binding.earniesValue.setTypeface(earniesTf);
        binding.earnies.setTypeface(earniesTf);
        binding.youGotTv.setTypeface(earniesTf);

        User user = Paper.book().read(EarineApp.KEY_CURRENT_USER);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            startAdvertService();
        }
    }
}
