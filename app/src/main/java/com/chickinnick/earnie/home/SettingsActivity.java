package com.chickinnick.earnie.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivitySettingBinding;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingBinding activitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
    }
}
