package com.chickinnick.earnie.enter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;

import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivitySigninBinding;
import com.chickinnick.earnie.enter.transition.DetailsTransition;
import com.chickinnick.earnie.home.WalletActivity;
import com.chickinnick.earnie.model.User;
import com.chickinnick.earnie.model.UserBuilder;
import com.chickinnick.earnie.social.FacebookManager;
import com.chickinnick.earnie.tutorial.TutorActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

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

        FacebookManager.getInstance().init();

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

        if (null == Paper.book().read(EarineApp.KEY_CURRENT_USER)) {
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
        } else {
            startActivity(new Intent(SigninActivity.this, WalletActivity.class));
        }
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
    public void onSignUp(User user) {
        Paper.book().write(EarineApp.KEY_CURRENT_USER, user);
        Intent intent = new Intent(SigninActivity.this, TutorActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLogIn(String email, String password) {

    }

    @Override
    public void onLogInFB() {
        FacebookManager.getInstance().setLoginCallback(facebookCallback);
        FacebookManager.getInstance().logIn(this);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FacebookManager.getInstance().onResult(requestCode, resultCode, data);

    }

    FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            FacebookManager.getInstance().updateStatus();
            if (loginResult.getAccessToken() != null) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                String email = null;
                                try {
                                    email = object.getString("email");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Profile currentProfile = Profile.getCurrentProfile();

                                UserBuilder builder = new UserBuilder()
                                        .setFirstName(currentProfile.getFirstName())
                                        .setLastName(currentProfile.getLastName())
                                        .setEmail(email)
                                        .setProfilePictureUri(currentProfile.getProfilePictureUri(128, 128));

                                onSignUp(builder.createUser());
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();


            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };
}
