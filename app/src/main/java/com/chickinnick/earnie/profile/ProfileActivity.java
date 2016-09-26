package com.chickinnick.earnie.profile;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivityProfileBinding;
import com.chickinnick.earnie.model.User;
import com.chickinnick.earnie.social.FacebookManager;

import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityProfileBinding activityProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_profile);
        Typeface typeface = EarineApp.getRegularTypeface();
        activityProfileBinding.logOutBtn.setTypeface(typeface);
        activityProfileBinding.logOutBtn.setOnClickListener(this);
        activityProfileBinding.drawerBtn.setOnClickListener(this);
        User user = Paper.book().read(EarineApp.KEY_CURRENT_USER);
        activityProfileBinding.setUser(user);
        if (null != user.getImageUri()) {
            Glide.with(this).load(user.getImageUri()).into(activityProfileBinding.profileImage);
        } else {
            Glide.with(this).load(R.mipmap.ic_launcher).into(activityProfileBinding.profileImage);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_btn:
                onBack();
                break;
            case R.id.log_out_btn:
                FacebookManager.getInstance().logOut();
                Paper.book().delete(EarineApp.KEY_CURRENT_USER);
                break;
        }
    }


    public void onBack() {
        moveTaskToBack(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }
}
