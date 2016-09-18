package com.chickinnick.earnie.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivitySettingBinding;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActivitySettingBinding activitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }
}
