package com.chickinnick.earnie.adcore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UserPresentReceiver extends BroadcastReceiver {


    private AdOverlayService adService;

    public UserPresentReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            if (null != adService) {
                adService.hideView();
            }
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            if (null != adService) {
                adService.showView();
            }
        } else if (intent.getAction().equals(OverlayService.ACTION_STOP_SELF)) {
            adService.stopSelf();
        }
    }

    void setAdService(AdOverlayService adService) {
        this.adService = adService;
    }
}
