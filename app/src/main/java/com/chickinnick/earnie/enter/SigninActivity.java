package com.chickinnick.earnie.enter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivitySigninBinding;
import com.chickinnick.earnie.enter.transition.DetailsTransition;
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
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        signInActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_signin);
        splashFragment = SplashFragment.newInstance();
        registerFragment = RegisterFragment.newInstance();
        signFragment = SignFragment.newInstance();
        signFragment.setOnFragmentActionListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            signFragment.setSharedElementEnterTransition(new DetailsTransition());
            signFragment.setEnterTransition(new Fade());
            splashFragment.setExitTransition(new Fade());
            signFragment.setSharedElementReturnTransition(new DetailsTransition());

        }


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
                fragmentTransaction.addSharedElement(splashFragment.getBinding().logo, "logotype");

                //TODO: transaction
                fragmentTransaction.replace(R.id.fragment_container, signFragment);
                fragmentTransaction.commit();

            }
        }, SPLASH_DELAY);

    }


    @Override
    public void onGoSignUp() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.add(R.id.fragment_container, registerFragment);
        fragmentTransaction.addToBackStack(null);

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
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        fragmentTransaction.add(R.id.fragment_container, termsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onForgotPassword() {

    }
}
