package com.example.streetlightfaultdetectionandlocationtracking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class GraphActivity extends AppCompatActivity {

    private WebView webView1, webView2, webView3, webView4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        webView1 = findViewById(R.id.webView1);
        webView2 = findViewById(R.id.webView2);
        webView3 = findViewById(R.id.webView3);
        webView4 = findViewById(R.id.webView4);

        // Load URLs into WebViews
        loadWebView(webView1, "https://thingspeak.com/channels/2561588/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");
        loadWebView(webView2, "https://thingspeak.com/channels/2561588/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");
        loadWebView(webView3, "https://thingspeak.com/channels/2561588/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");
        loadWebView(webView4, "https://thingspeak.com/channels/2561588/charts/4?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");


    }

    private void loadWebView(WebView webView, String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setUseWideViewPort(true);

        webView.loadUrl(url);
    }
}
