package com.chickinnick.earnie.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivitySettingBinding;
import com.chickinnick.earnie.payment.PaymentActivity;
import com.chickinnick.earnie.profile.ProfileActivity;

import io.paperdb.Paper;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private ActivitySettingBinding activitySettingsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        activitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        Typeface typeface = EarineApp.getRegularTypeface();
        activitySettingsBinding.paymentMethods.setTypeface(typeface);
        activitySettingsBinding.profile.setTypeface(typeface);
        activitySettingsBinding.about.setTypeface(typeface);
        activitySettingsBinding.adCategories.setTypeface(typeface);
        activitySettingsBinding.adFreqTitle.setTypeface(typeface);

        activitySettingsBinding.drawerBtn.setOnClickListener(this);
        activitySettingsBinding.paymentMethods.setOnClickListener(this);
        activitySettingsBinding.profile.setOnClickListener(this);


        activitySettingsBinding.adFreqSeekbar.setProgress(1);
        activitySettingsBinding.adFreqSeekbar.setOnSeekBarChangeListener(this);


        activitySettingsBinding.low.setOnClickListener(indicatorClick);
        activitySettingsBinding.norm.setOnClickListener(indicatorClick);
        activitySettingsBinding.high.setOnClickListener(indicatorClick);

        activitySettingsBinding.switchUnlockSetting.setTypeface(typeface);
        activitySettingsBinding.switchUnlockSetting.setChecked(Paper.book().read(EarineApp.KEY_AD_MODE, true));
        activitySettingsBinding.switchUnlockSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Paper.book().write(EarineApp.KEY_AD_MODE, isChecked);
            }
        });

    }


    View.OnClickListener indicatorClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.low:
                    activitySettingsBinding.adFreqSeekbar.setProgress(0);
                    break;
                case R.id.norm:
                    activitySettingsBinding.adFreqSeekbar.setProgress(1);
                    break;
                case R.id.high:
                    activitySettingsBinding.adFreqSeekbar.setProgress(2);
                    break;
            }
        }
    };


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.payment_methods:
                startActivity(new Intent(SettingsActivity.this, PaymentActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.drawer_btn:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (progress) {
            case 0:
                activitySettingsBinding.low.setTextColor(getResources().getColor(R.color.toolbar_title_blue));
                activitySettingsBinding.norm.setTextColor(getResources().getColor(R.color.text_default_dark));
                activitySettingsBinding.high.setTextColor(getResources().getColor(R.color.text_default_dark));
                break;
            case 1:
                activitySettingsBinding.low.setTextColor(getResources().getColor(R.color.text_default_dark));
                activitySettingsBinding.norm.setTextColor(getResources().getColor(R.color.toolbar_title_blue));
                activitySettingsBinding.high.setTextColor(getResources().getColor(R.color.text_default_dark));
                break;
            case 2:
                activitySettingsBinding.low.setTextColor(getResources().getColor(R.color.text_default_dark));
                activitySettingsBinding.norm.setTextColor(getResources().getColor(R.color.text_default_dark));
                activitySettingsBinding.high.setTextColor(getResources().getColor(R.color.toolbar_title_blue));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
