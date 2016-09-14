package com.chickinnick.earnie;

import android.app.Application;

import io.paperdb.Paper;

public class EarineApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);

    }
}
