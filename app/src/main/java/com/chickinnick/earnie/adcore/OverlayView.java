package com.chickinnick.earnie.adcore;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.chickinnick.earnie.R;

import io.paperdb.Paper;

public abstract class OverlayView extends RelativeLayout {

    public static final String KEY_SAVED_ViEW_POSITION = "saved_x_y";
    protected WindowManager.LayoutParams layoutParams;

    private int layoutResId;
    private int notificationId = 0;

    public OverlayView(OverlayService service, final int layoutResId, int notificationId) {
        super(service);
        this.layoutResId = layoutResId;
        this.notificationId = notificationId;
        setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = layoutParams.x;
                        initialY = layoutParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        layoutParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        layoutParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                        Pair<Integer, Integer> savedCoords = new Pair<Integer, Integer>(layoutParams.x, layoutParams.y);
                        Paper.book().write(KEY_SAVED_ViEW_POSITION, savedCoords);
                        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                                .updateViewLayout(OverlayView.this, layoutParams);
                        return true;
                }
                return false;
            }
        });


        load();
    }

    public OverlayService getService() {
        return (OverlayService) getContext();
    }

    private void setupLayoutParams() {
        int imW = getResources().getDimensionPixelSize(R.dimen.image_w);
        int imH = getResources().getDimensionPixelSize(R.dimen.image_h);
        layoutParams = new WindowManager.LayoutParams(imW, imH,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        Pair<Integer, Integer> savedXY = Paper.book().read(KEY_SAVED_ViEW_POSITION, null);
        if (null != savedXY) {
            layoutParams.x = savedXY.first;
            layoutParams.y = savedXY.second;
        }
    }

    private void inflateView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutResId, this);
        onInflateView();
    }


    abstract void onInflateView();



    protected void addView() {
        setupLayoutParams();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).addView(this, layoutParams);
        super.setVisibility(View.GONE);
    }

    protected void load() {
        inflateView();
        addView();
    }

    public void destory() {
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).removeView(this);
    }


    protected void hide() {
        super.setVisibility(View.GONE);
    }

    protected void show() {
        super.setVisibility(View.VISIBLE);
    }

}
