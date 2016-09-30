package com.chickinnick.earnie.adcore;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.chickinnick.earnie.R;

public class AdvertismentView extends OverlayView {

    public static final String TAG = AdvertismentView.class.getSimpleName();
    private WebView webView;
    private OnIteractionCallback onIteractionCallback;

    private ProgressBar progress;

    public AdvertismentView(OverlayService service) {
        super(service, R.layout.advertisment_layout, 1);
    }

    public int getGravity() {
        return Gravity.TOP + Gravity.RIGHT;
    }

    @Override
    protected void onInflateView() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                onIteractionCallback.onWebViewClicked(url);
                return false;
            }
        };

        webView = (WebView) this.findViewById(R.id.webview);
        progress = (ProgressBar) this.findViewById(R.id.progress_hide);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl("http://www.metrolyrics.com/top100.html");
    }

    @Override
    public void updateProgress(final int counter) {
        Log.d(TAG, "try upd progress");

        if (null != progress) {
            Log.d(TAG, "progress:" + counter);
            progress.setProgress(counter);
        }
    }

    public void setOnIteractionCallback(OnIteractionCallback onIteractionCallback) {
        this.onIteractionCallback = onIteractionCallback;
    }

    public interface OnIteractionCallback {
        void onWebViewClicked(String url);
    }
}
