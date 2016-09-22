package com.chickinnick.earnie.adcore;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Pair;
import android.view.Gravity;
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

    public int getLayoutGravity() {
        return Gravity.CENTER;
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
        }// layoutParams.gravity = getLayoutGravity();
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

    protected void unload() {
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).removeView(this);
        removeAllViews();
    }

    protected void reload() {
        unload();
        load();
    }

    public void destory() {
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).removeView(this);
    }



    protected boolean showNotificationHidden() {
        // Override this to configure the notification to remain even when the
        // overlay is invisible.
        return true;
    }

    protected boolean onVisibilityToChange(int visibility) {
        return true;
    }

    protected View animationView() {
        return this;
    }

    protected void hide() {
        super.setVisibility(View.GONE);
    }

    protected void show() {
        super.setVisibility(View.VISIBLE);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.VISIBLE) {
            getService().moveToForeground(notificationId, !showNotificationHidden());
        } else {
            getService().moveToBackground(notificationId, !showNotificationHidden());
        }

        if (getVisibility() != visibility) {
            if (onVisibilityToChange(visibility)) {
                super.setVisibility(visibility);
            }
        }
    }

    protected int getLeftOnScreen() {
        int[] location = new int[2];

        getLocationOnScreen(location);

        return location[0];
    }

    protected int getTopOnScreen() {
        int[] location = new int[2];

        getLocationOnScreen(location);

        return location[1];
    }

    protected boolean isInside(View view, float x, float y) {
        // Use this to test if the X, Y coordinates of the MotionEvent are
        // inside of the View specified.
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (x >= location[0]) {
            if (x <= location[0] + view.getWidth()) {
                if (y >= location[1]) {
                    if (y <= location[1] + view.getHeight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
