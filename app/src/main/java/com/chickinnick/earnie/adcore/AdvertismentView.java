package com.chickinnick.earnie.adcore;

import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;

import com.chickinnick.earnie.R;

public class AdvertismentView extends OverlayView {


    private WebView webView;

    public AdvertismentView(OverlayService service) {
        super(service, R.layout.advertisment_layout, 1);
    }


    public int getGravity() {
        return Gravity.TOP + Gravity.RIGHT;
    }

    @Override
    protected void onInflateView() {
        webView = (WebView) this.findViewById(R.id.cover_layout);
        webView.loadUrl("http://www.metrolyrics.com/top100.html");
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

    }
}
