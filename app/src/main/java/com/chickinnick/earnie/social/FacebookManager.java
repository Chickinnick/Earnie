package com.chickinnick.earnie.social;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class FacebookManager implements FacebookCallback<LoginResult> {
    private static final String TAG = FacebookManager.class.getSimpleName();
    private static FacebookManager ourInstance = new FacebookManager();

    public static final String[] permission = {"public_profile", "email"};

    public static FacebookManager getInstance() {
        return ourInstance;
    }

    private FacebookManager() {
    }

    public void init() {
        callbackManager = CallbackManager.Factory.create();
    }

    public void logIn(Activity activity) {
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList(permission));
    }

    public void logOut() {
        LoginManager.getInstance().logOut();
    }


    public void setLoginCallback(FacebookCallback<LoginResult> loginCallback) {
        if (null == loginCallback) {
            LoginManager.getInstance().registerCallback(callbackManager, this);
        } else {
            LoginManager.getInstance().registerCallback(callbackManager, loginCallback);
        }
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY);
    }

    private boolean isLoggedIn;

    private CallbackManager callbackManager;

    public void updateStatus() {
        Log.w(TAG, "update status ");
        boolean isTokenValid = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();
        if (isTokenValid && profile != null) {
            isLoggedIn = true;
        } else {
            isLoggedIn = false;
        }
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.w(TAG, "LoginManager FacebookCallback onSuccess");

        Log.w(TAG, "Access Token:: " + loginResult.getAccessToken());
        updateStatus();
        if (loginResult.getAccessToken() != null) {

        }
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onError(FacebookException exception) {
    }


    private boolean hasPublishPermission() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && accessToken.getPermissions().contains("publish_actions");
    }


    public boolean isLoggedIn() {
        updateStatus();
        return isLoggedIn;
    }
}