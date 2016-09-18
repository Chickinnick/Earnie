package com.chickinnick.earnie;

import android.app.Application;
import android.graphics.Typeface;

import io.paperdb.Paper;

public class EarineApp extends Application {

    private static Typeface typefaceRegular;
    private static Typeface typefaceBold;
    private static Typeface typefaceLight;

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
        typefaceRegular = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.ttf");
        typefaceBold = Typeface.createFromAsset(getAssets(), "Quicksand-Bold.ttf");
        typefaceLight = Typeface.createFromAsset(getAssets(), "Quicksand-Light.ttf");
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
