package com.chickinnick.earnie;

import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Typeface;

import com.facebook.FacebookSdk;

import io.paperdb.Paper;

public class EarineApp extends Application {

    public static final String KEY_CURRENT_USER = "key_current_user";
    public static final String KEY_AD_MODE = "ad_mode";
    public static final String KEY_IS_LOCKED = "is_locked";
    private static Typeface typefaceRegular;
    private static Typeface typefaceBold;
    private static Typeface typefaceLight;

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
        FacebookSdk.sdkInitialize(this);
        typefaceRegular = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.ttf");
        typefaceBold = Typeface.createFromAsset(getAssets(), "Quicksand-Bold.ttf");
        typefaceLight = Typeface.createFromAsset(getAssets(), "Quicksand-Light.ttf");

        KeyguardManager km = (KeyguardManager)getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
            Paper.book().write(KEY_IS_LOCKED , km.isKeyguardSecure());


    }

    public static Typeface getRegularTypeface() {
        return typefaceRegular;
    }

    public static Typeface getBoldTypeface() {
        return typefaceBold;
    }

    public static Typeface getLightTypeface() {
        return typefaceLight;
    }


}
