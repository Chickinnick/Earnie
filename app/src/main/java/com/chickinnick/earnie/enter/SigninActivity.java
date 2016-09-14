package com.chickinnick.earnie.enter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivitySigninBinding;

public class SigninActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 1500;
    private SplashFragment splashFragment;
    private ActivitySigninBinding signInActivityBinding;

    Handler handler = new Handler();
    private SignFragment signFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_signin);
        splashFragment = SplashFragment.newInstance();
        signFragment = SignFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, splashFragment);
        fragmentTransaction.commit();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                 final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addSharedElement(splashFragment.getBinding().logo, "logo");
                 fragmentTransaction.replace(R.id.fragment_container, signFragment );
                 fragmentTransaction.commit();
            }
        }, SPLASH_DELAY );

    }
}
