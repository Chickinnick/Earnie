package com.chickinnick.earnie.adcore;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public abstract class OverlayView extends RelativeLayout implements View.OnClickListener {
    protected WindowManager.LayoutParams layoutParams;

    private int layoutResId;
    private int notificationId = 0;

    public OverlayView(OverlayService service, int layoutResId, int notificationId) {
        super(service);
        this.layoutResId = layoutResId;
        this.notificationId = notificationId;
        this.setOnClickListener(this);
        load();
    }

    public OverlayService getService() {
        return (OverlayService) getContext();
    }

    public int getLayoutGravity() {
        return Gravity.CENTER;
    }

    private void setupLayoutParams() {
        layoutParams = new WindowManager.LayoutParams(800, 800,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        layoutParams.gravity = getLayoutGravity();
    }

    private void inflateView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutResId, this);
        onInflateView();
    }


    abstract void onInflateView();

    abstract void onClick();

    public boolean isVisible() {
        // Override this method to control when the Overlay is visible without
        // destroying it.
        return true;
    }

    public void refreshLayout() {
        if (isVisible()) {
            removeAllViews();
            inflateView();
            ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).updateViewLayout(this, layoutParams);
            refresh();
        }
    }

    protected void addView() {
        setupLayoutParams();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).addView(this, layoutParams);
        super.setVisibility(View.GONE);
    }

    protected void load() {
        inflateView();
        addView();
        refresh();
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

    public void refresh() {
        if (!isVisible()) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
        }
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

    protected boolean isInside(View view, int x, int y) {
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


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        onClick();
    }
}
