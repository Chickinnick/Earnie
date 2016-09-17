package com.chickinnick.earnie.enter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivitySigninBinding;
import com.chickinnick.earnie.tutorial.TutorActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SigninActivity extends AppCompatActivity implements FragmentActionListener {

    private static final long SPLASH_DELAY = 1500;
    private SplashFragment splashFragment;
    private ActivitySigninBinding signInActivityBinding;
    private SignFragment signFragment;
    private RegisterFragment registerFragment;
    private TermsFragment termsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_signin);
        splashFragment = SplashFragment.newInstance();
        registerFragment = RegisterFragment.newInstance();
        signFragment = SignFragment.newInstance();
        signFragment.setOnFragmentActionListener(this);

        termsFragment = TermsFragment.newInstance();
        termsFragment.setOnFragmentActionListener(this);
        registerFragment.setOnFragmentActionListener(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, splashFragment);
        fragmentTransaction.commit();


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.addSharedElement(splashFragment.getBinding().logo, "logo");TODO: transaction
                fragmentTransaction.replace(R.id.fragment_container, signFragment);
                fragmentTransaction.commit();

            }
        }, SPLASH_DELAY);

    }


    @Override
    public void onGoSignUp() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, registerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSignUp() {
        Intent intent = new Intent(SigninActivity.this, TutorActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLogIn(String email, String password) {

    }

    @Override
    public void onLogInFB() {

    }

    @Override
    public void onLogInGoogle() {

    }

    @Override
    public void onGoReadTerms() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, termsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onForgotPassword() {

    }
}
