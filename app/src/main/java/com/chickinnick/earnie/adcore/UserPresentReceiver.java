package com.chickinnick.earnie.adcore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;

import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.home.WalletActivity;
import com.chickinnick.earnie.model.User;

import io.paperdb.Paper;

public class UserPresentReceiver extends BroadcastReceiver {


    private AdOverlayService adService;

    public UserPresentReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isLockScreen = Paper.book().read(EarineApp.KEY_AD_MODE);

        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            if (null != adService && isLockScreen) {
                adService.hideView();
            } else if (null != adService) {
                adService.showView();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adService.hideView();
                    }
                }, 4000);
            }
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            if (null != adService && isLockScreen) {
                adService.showView();
            }
        } else if (intent.getAction().equals(OverlayService.ACTION_STOP_SELF)) {
            adService.stopSelf();
        } else if (intent.getAction().equals(OverlayService.ACTION_SHOW_VIEW)) {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            Intent browserIntent = new Intent(context, WalletActivity.class);
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            browserIntent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED +
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD +
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON +
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

            User user = Paper.book().read(EarineApp.KEY_CURRENT_USER);
            user.incrementEarnie();
            Paper.book().write(EarineApp.KEY_CURRENT_USER, user);

            context.startActivity(browserIntent);
        }
    }

    void setAdService(AdOverlayService adService) {
        this.adService = adService;
    }
}
