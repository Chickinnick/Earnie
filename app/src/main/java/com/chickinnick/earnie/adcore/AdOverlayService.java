package com.chickinnick.earnie.adcore;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.chickinnick.earnie.R;

public class AdOverlayService extends OverlayService {

    private AdvertismentView overlayView;
    private UserPresentReceiver userPresentReceiver;
    private IntentFilter intentFilter;

    @Override
    public void onCreate() {
        super.onCreate();
        userPresentReceiver = new UserPresentReceiver();
        userPresentReceiver.setAdService(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(ACTION_STOP_SELF);
        intentFilter.addAction(ACTION_SHOW_VIEW);
        registerReceiver(userPresentReceiver, intentFilter);


        overlayView = new AdvertismentView(this);
        overlayView.setOnIteractionCallback(new AdvertismentView.OnIteractionCallback() {
            @Override
            public void onWebViewClicked(String url) {

                sendBroadcast(new Intent(ACTION_SHOW_VIEW));
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                Intent browserIntent = new Intent(AdOverlayService.this, WalletActivity.class);
//                startActivity(browserIntent);
            }
        });
        hideView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (overlayView != null) {
            overlayView.destory();
        }

    }

    @Override
    protected Notification foregroundNotification(int notificationId) {
        Notification.Builder builder = new Notification.Builder(AdOverlayService.this);
        builder.setAutoCancel(false);
        builder.setContentTitle("Earnie service");
        builder.setContentText("Tap to stop service");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(notificationIntent());
        builder.setOngoing(true);
        return builder.build();
    }


    private PendingIntent notificationIntent() {
        Intent intent = new Intent(ACTION_STOP_SELF);
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pending;
    }

    public void hideView() {
        overlayView.setVisibility(View.GONE);
    }

    public void showView() {
        overlayView.setVisibility(View.VISIBLE);
    }

}
