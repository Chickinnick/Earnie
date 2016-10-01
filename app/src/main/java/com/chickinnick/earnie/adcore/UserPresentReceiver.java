package com.chickinnick.earnie.adcore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.WindowManager;

import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.model.User;

import io.paperdb.Paper;

public class UserPresentReceiver extends BroadcastReceiver {

    public static final String TAG = UserPresentReceiver.class.getSimpleName();

    private AdOverlayService adService;

    public UserPresentReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isShowOnMainScreen = Paper.book().read(EarineApp.KEY_AD_MODE, true);

        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            if (null != adService && !isShowOnMainScreen) {
                Log.d(TAG, "user present hide lockscreen:" + isShowOnMainScreen);
                adService.hideView();
            } else if (null != adService) {
                Log.d(TAG, "user present show full lockscreen:" + isShowOnMainScreen);
                adService.showViewFullScreen();
                adService.startHideTimeout();
            }
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            if (null != adService && !isShowOnMainScreen) {
                adService.lockScreen();
                adService.showSmallView();
                Log.d(TAG, "screen on show:" + isShowOnMainScreen);
            }
        } else if (intent.getAction().equals(OverlayService.ACTION_STOP_SELF)) {
            adService.stopSelf();
        } else if (intent.getAction().equals(OverlayService.ACTION_SHOW_VIEW)) {
           String url =  intent.getStringExtra(AdOverlayService.KEY_EXTRA_URL);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            //todo open browser
          //  Intent browserIntent = new Intent(context, WalletActivity.class);
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            browserIntent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED +
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD +
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    +
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            User user = Paper.book().read(EarineApp.KEY_CURRENT_USER);
            user.incrementEarnie();
            Paper.book().write(EarineApp.KEY_CURRENT_USER, user);
            adService.unlockScreen();
            context.startActivity(browserIntent);
        }
    }

    void setAdService(AdOverlayService adService) {
        this.adService = adService;
    }
}
